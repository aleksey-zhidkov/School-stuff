package com.dob.ipc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.*;

public class IPCLinkImpl implements IPCLink, Runnable {

    private final Socket socket;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;

    private final Map<Long, Serializable> responses = new Hashtable<Long, Serializable>();

    private final Thread workingThread = new Thread(this);

    private final List<IPCLinkListener> listeners = new ArrayList<IPCLinkListener>();

    private Long id = 0L;
    private static final String FAKE_RESPONSE = "null_response";

    public IPCLinkImpl(Socket socket) throws IOException {
        this.socket = socket;

        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

        workingThread.start();
    }

    public void close() throws IOException {
        socket.close();
        workingThread.interrupt();
    }

    public synchronized void write(Serializable message) throws IOException {
        output.writeObject(message);
        output.flush();
        output.reset();
        System.out.println(Thread.currentThread().getName() + "|IPCLink| Message " + message + " writed to " + socket.toString());
    }

    public Serializable sendRequest(Serializable request) throws IOException {
        return sendRequest(request, 0);
    }

    public Serializable sendRequest(Serializable request, long timeout) throws IOException {
        IPCRequest requestMsg;
        synchronized (this) {
            requestMsg = new IPCRequest(id++, request);
            write(requestMsg);
            responses.put(requestMsg.getId(), FAKE_RESPONSE);
        }
        long exitTime = System.currentTimeMillis() + timeout;
        while (responses.get(requestMsg.getId()) == FAKE_RESPONSE &&
                (exitTime > System.currentTimeMillis() || timeout == 0)) {
            try {
                synchronized (this) {
                    System.out.println(System.currentTimeMillis() + " Wait for response to: " + requestMsg.getId() + " for " + timeout + " millis, " + responses);
                    wait(timeout);
                    System.out.println(System.currentTimeMillis() + " exit wait for response to: " + requestMsg.getId() + " for " + timeout + " millis, " + responses);
                }
            } catch (InterruptedException e) {
                break;
            }
            System.out.println("Responses: " + responses);
        }

        System.out.println(System.currentTimeMillis() + " Getting response for: " + requestMsg.getId() + ", " + responses);
        return responses.remove(requestMsg.getId());
    }

    public void run() {
        while (true) {
            try {
                final Object message = input.readObject();
                System.out.println(Thread.currentThread().getName() + "|IPCLink| Message recieved: " + message);
                if (message instanceof IPCResponse) {
                    final IPCResponse response = (IPCResponse) message;
                    synchronized (this) {
                        responses.put(response.getId(), response.getMessage());
                        System.out.println(System.currentTimeMillis() + " Putted response: " + responses);
                        notifyAll();
                    }
                    continue;
                }

                for (IPCLinkListener listener : listeners) {
                    listener.newMessage(this, message);
                }

            } catch (IOException e) {
                try {
                    System.out.println("Link closed");
                    close();
                } catch (IOException ignore) {
                }
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isClosed() {
        return socket.isClosed();
    }

    public void addListener(IPCLinkListener listener) {
        if (listeners.contains(listener)) {
            return;
        }

        listeners.add(listener);
    }

    public void removeListner(IPCLinkListener listener) {
        listeners.remove(listener);
    }
}
