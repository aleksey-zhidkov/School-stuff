package com.dob.demo.file;

import java.util.List;
import java.util.ArrayList;

public class WorkerThread extends Thread {

    private final List<Runnable> tasks = new ArrayList<Runnable>();

    public void run() {
        while (true) {
            Runnable task = null;
            synchronized (this) {
                if (tasks.size() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        System.out.println("Working thread interrupted");
                        break;
                    }
                    task = tasks.remove(0);
                }
            }
            if (task != null) {
                task.run();
            }
        }
    }

    public void addTask(Runnable task) {
        synchronized (this) {
            tasks.add(task);
            notify();
        }
    }

}
