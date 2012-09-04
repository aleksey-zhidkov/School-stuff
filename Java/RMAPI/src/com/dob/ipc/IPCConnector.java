package com.dob.ipc;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.*;

public class IPCConnector implements Runnable {

    private static final int APP_ID_PREFIX = 10000;

    private final ServerSocket socket;
    private final IPCConnectorListener listener;
    private final Thread workingThread = new Thread(this);
    private final List<IPCLink> links = new ArrayList<IPCLink>();

    public IPCConnector(int appId, IPCConnectorListener listener) throws IOException {
        this.listener = listener;
        socket = new ServerSocket(APP_ID_PREFIX + appId);
        workingThread.start();
    }

    public void run() {
        while (true) {
            try {
                Socket sock = socket.accept();
                IPCLink link = new IPCLinkImpl(sock);
                listener.newLink(link);
            } catch(InterruptedIOException e) {
                break;
            } catch (IOException e) {
                if (socket.isClosed()) {
                    break;
                }
            }
        }
    }

    public void close() throws IOException {
        workingThread.interrupt();
        socket.close();
        for (IPCLink link : links) {
            link.close();
        }
        System.out.println("Connector closed");
    }

    public IPCLink open(int appId) throws IOException {
        Socket sock = new Socket("localhost", APP_ID_PREFIX + appId);
        final IPCLinkImpl ipcLink = new IPCLinkImpl(sock);
        links.add(ipcLink);
        return ipcLink;
    }

}
