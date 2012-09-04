package ru.jdev.fit.opp;

import ru.jdev.net.listener.build.NetListenerBuilderConfiguration;
import ru.jdev.net.listener.build.NetListenerBuilder;
import ru.jdev.net.listener.client.ListenerClient;
import ru.jdev.net.listener.io.channels.ChannelDescriptor;
import ru.jdev.net.listener.NetListener;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * @Author: jdev
 * Date: 23.05.2007
 */
public class MPIRunner implements ListenerClient
{
    private static String clazz;
    private static int tCount;
    private boolean isDone;
    private NetListener listener;
    private ChannelDescriptor[] clients;
    private List<OutputReader> outputReaders = new ArrayList<OutputReader>();
    private int clientsCount = -1;

    public static void main(String[] args)
    {
        MPIRunner m = new MPIRunner();
        clazz = args[0];
        tCount = Integer.parseInt(args[1]);
        m.run();
    }

    public void run()
    {
        clients = new ChannelDescriptor[tCount];
        NetListenerBuilderConfiguration cfg = new NetListenerBuilderConfiguration();
        cfg.setClient(this);
        cfg.setPort(1986);
        cfg.setServerMode(Boolean.TRUE);

        NetListenerBuilder b = new NetListenerBuilder(cfg);
        listener = b.build();
        try
        {
            listener.initListener();
            new Thread(listener).start();

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        for (int i = 0; i < tCount; i++)
        {
            try
            {
                Process p = Runtime.getRuntime().exec("\"C:\\Program Files\\Java\\jdk1.6.0\\bin\\java\" -cp \"D:\\lexx\\MW\\Programming\\Java\\FIT\\classes;D:\\lexx\\MW\\Programming\\Java\\libs\\NetListener\\classes\" "+clazz);
                OutputReader reader = new OutputReader(p.getInputStream(), i);
                reader.start();
                outputReaders.add(reader);
                reader = new OutputReader(p.getErrorStream(), i);
                reader.start();
                outputReaders.add(reader);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        while(!isDone)
        {
            try
            {
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        listener.stopServer();
        for (OutputReader or : outputReaders )
        {
            or.interrupt();
        }
    }

    public void initializationException(Throwable t)
    {
        t.printStackTrace();
    }

    public void listenException(Throwable t)
    {
        t.printStackTrace();
    }

    public void finalizeException(Throwable t)
    {
        t.printStackTrace();
    }

    public void inputData(ChannelDescriptor desc, byte[] buff)
    {
        int idx = buff[0];
        ChannelDescriptor cd = clients[idx];
        byte[] r = new byte[buff.length - 1];
        System.arraycopy(buff, 1, r, 0, r.length);
        try
        {
            listener.write(cd, r);
            System.out.println(new String(r));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void newChannel(ChannelDescriptor desc)
    {
        System.out.println("New channel!");
        clients[desc.getId()] = desc;
        if (++clientsCount == 0)
        {
            clientsCount++;
        }
        try
        {
            listener.write(desc, new byte[]{(byte) desc.getId(), (byte) tCount});
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void channelClosed(ChannelDescriptor desc)
    {
        System.out.println("Channel closed!");
        if (--clientsCount == 0)
        {
            isDone = true;
        }
    }
}
