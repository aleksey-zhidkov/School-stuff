package ru.jdev.fit.opp;

import java.io.InputStream;
import java.io.IOException;

/**
 * @Author: jdev
 * Date: 23.05.2007
 */
public class OutputReader extends Thread
{

    private InputStream in;
    private Integer id;

    public OutputReader(InputStream in, Integer id)
    {
        this.in = in;
        this.id = id;
    }


    public void run()
    {
        int len;
        byte[] buff = new byte[256];
        try
        {
            while (!isInterrupted())
            {
                len = in.read(buff);
                if (len == -1)
                {
                    try
                    {
                        Thread.sleep(500);
                    } catch (InterruptedException e)
                    {
                        break;
                    }
                    continue;
                }
                System.out.println("-- "+ id + ":" + new String(buff, 0, len - 1));

            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
