package com.dob.demo.file;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileWriterThread extends Thread {

    private final DateFormat dateFormat = new SimpleDateFormat("[kk:mm:ss] ");
    private final BufferedOutputStream bos;
    private boolean runned = true;

    public FileWriterThread(BufferedOutputStream bos) {
        this.bos = bos;
    }

    public void stopThread() {
        runned = false;
    }

    public void run() {

        while (runned) {
            try {
                bos.write((dateFormat.format(new Date()) + "record from application ¹" + ClientApp.APP_ID + "\r\n").getBytes());
                bos.flush();
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        try {
            bos.close();
        } catch (IOException ignore) {
        }
        System.out.println("File getted out");
    }
}