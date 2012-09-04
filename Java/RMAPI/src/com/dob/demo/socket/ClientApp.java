package com.dob.demo.socket;

import org.davic.resources.ResourceProxy;
import com.dob.resources.ResourceConsumer;
import com.dob.resources.ResourceTypes;
import com.dob.resources.ResourceDescriptor;
import com.dob.resources.ResourceException;
import com.dob.resources.manager.ResourceManager;
import com.dob.resources.manager.ResourceManagerRegistryFactory;
import com.dob.resources_impl.ServerSocketProxy;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

public class ClientApp implements ResourceConsumer, Runnable {

    private ServerSocket socket;
    private int appId = (int) (Math.random() * 50 + 1);

    public int getAppId() {
        return appId;
    }

    public int getPriority() {
        return 0;
    }

    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
        boolean answer = Math.random() > 0.5;
        System.out.println("Answer for request release: " + answer);
        if (answer) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return answer;
    }

    public void release(ResourceProxy proxy) {
        try {
            socket.close();
            System.out.println("Server socket released");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyReleased(ResourceProxy proxy) {
        System.out.println("Notify released");
    }

    public void run() {
        ResourceManager resourceManager = ResourceManagerRegistryFactory.getRegistry().getResourceManager(ResourceTypes.RESOURCE_SERVER_SOCKET);
        try {
            ResourceProxy proxy = resourceManager.reserve(this, new ResourceDescriptor("1986", ResourceTypes.RESOURCE_SERVER_SOCKET));
            if (proxy == null) {
                return;
            }
            socket = ((ServerSocketProxy) proxy).getSocket();
            System.out.println("accepting socket");
            Socket newSocket = socket.accept();
            System.out.println("New socket: " + newSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResourceException e) {
            e.printStackTrace();
        }
        System.out.println("Exit");
    }

    public static void main(String[] args) {
        ClientApp app = new ClientApp();
        new Thread(app).start();
    }

}
