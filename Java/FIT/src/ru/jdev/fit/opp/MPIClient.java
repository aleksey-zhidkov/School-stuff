package ru.jdev.fit.opp;

import ru.jdev.net.listener.build.NetListenerBuilderConfiguration;
import ru.jdev.net.listener.build.NetListenerBuilder;
import ru.jdev.net.listener.client.ListenerClient;
import ru.jdev.net.listener.io.channels.ChannelDescriptor;
import ru.jdev.net.listener.NetListener;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

/**
 * @Author: jdev
 * Date: 23.05.2007
 */
public class MPIClient implements ListenerClient
{
    private NetListener listener;
    private Integer id;
    private List<byte[]> dataQueue = new ArrayList<byte[]>();
    private ChannelDescriptor cd;
    private Integer pCount;



    public void init()
    {
        NetListenerBuilderConfiguration config = new NetListenerBuilderConfiguration();
        config.setClient(this);
        config.setNeedToCheckCnannels(false);
        config.setServerMode(Boolean.FALSE);
        NetListenerBuilder builder = new NetListenerBuilder(config);
        listener = builder.build();
        try
        {
            listener.initListener();
            new Thread(listener).start();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        while(id == null)
        {
            try
            {
                Thread.sleep(100);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void send(byte[] buff)
    {
        try
        {
            listener.write(cd, buff);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public byte[] recive()
    {
        synchronized(dataQueue)
        {
            if (dataQueue.size() == 0)
            {
                try
                {
                    dataQueue.wait();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            return dataQueue.remove(0);
        }
    }

    public void close()
    {
        listener.stopServer();
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
        if (id == null)
        {
            id = new Integer(buff[0]);
            pCount = new Integer(buff[1]);
        }
        synchronized(dataQueue)
        {
            dataQueue.add(buff);
            dataQueue.notify();
        }
    }

    public void newChannel(ChannelDescriptor desc)
    {
        cd = desc;
    }

    public void channelClosed(ChannelDescriptor desc)
    {}

    public Integer getId()
    {
        return id;
    }

    public int getPCount()
    {
        return pCount;
    }
}
