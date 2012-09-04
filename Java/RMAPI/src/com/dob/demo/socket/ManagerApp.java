package com.dob.demo.socket;

import com.dob.resources.manager.ResourceManagerRegistry;
import com.dob.resources.manager.ResourceManagerRegistryFactory;
import com.dob.resources.SocketIPCHandler;
import com.dob.resources.IPCHandlerListener;
import com.dob.resources.ResourceConsumer;
import com.dob.resources.ResourceDescriptor;

public class ManagerApp implements IPCHandlerListener {

    private ResourceManagerRegistry registry = ResourceManagerRegistryFactory.getRegistry();

    private void init() {
        SocketIPCHandler server = new SocketIPCHandler();
        server.setListener(this);
        server.init();
    }

    public void connectorOpened() {
        System.out.println("Connector opened");
    }

    public void newLink() {
        System.out.println("New link");
    }

    public void delegateRecieved(ResourceConsumer conumer) {
        System.out.println("Delegate recieved: " + conumer);
    }

    public void releaseMessageReceived() {
        System.out.println("Release message recieved");
    }

    public void resourceRequested(ResourceDescriptor resource, ResourceConsumer consumer) {
        System.out.println("Resource requested");
    }

    public void releaseRequestDenied() {
        System.out.println("Release request denied");
    }

    public void releaseRequestAccepted() {
        System.out.println("Release request accepted");
    }

    public static void main(String[] args) {
        ManagerApp app = new ManagerApp();
        app.init();
    }

}
