package com.dob.resources;

import com.dob.ipc.*;
import com.dob.commands.ResourceReleased;
import com.dob.commands.RequestResource;
import com.dob.resources.manager.ResourceManager;
import com.dob.resources.manager.ResourceManagerRegistry;
import com.dob.resources.manager.ResourceManagerRegistryFactory;

import java.io.IOException;
import java.util.HashMap;

import org.davic.resources.ResourceProxy;

public class SocketIPCHandler implements IPCConnectorListener, IPCLinkListener, IPCHandler {

    private static final ResourceManagerRegistry registry = ResourceManagerRegistryFactory.getRegistry();
    private final HashMap<IPCLink, ResourceConsumerDelegate> links = new HashMap<IPCLink, ResourceConsumerDelegate>();

    private IPCHandlerListener listener;

    public void setListener(IPCHandlerListener listener) {
        this.listener = listener;
    }

    public void init() {
        try {
            new IPCConnector(0, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newLink(IPCLink link) {
        if (listener != null) {
            listener.newLink();
        }
        link.addListener(this);
    }

    public boolean newMessage(IPCLink link, Object message) {
        final ResourceConsumerDelegate consumer = links.get(link);
        if (message instanceof IPCRequest) {
            handleRequest((IPCRequest) message, consumer);
        }
        if (message instanceof ResourceConsumerDelegate) {
            if (listener != null) {
                listener.delegateRecieved((ResourceConsumer) message);
            }
            ((ResourceConsumerDelegate) message).setLink(link);
            links.put(link, (ResourceConsumerDelegate) message);
            ((ResourceConsumerDelegate) message).setLink(link);
        } else if (message instanceof ResourceReleased) {
            if (listener != null) {
                listener.releaseMessageReceived();
            }
            final ResourceDescriptor descriptor = ((ResourceReleased) message).getDescriptor();
            ResourceManager resourceManager = registry.getResourceManager(descriptor.getType());
            try {
                resourceManager.notifyReleased(descriptor);
            } catch (ResourceException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }

        return true;
    }

    private void handleRequest(IPCRequest message, ResourceConsumerDelegate consumer) {
        if (message.getRequest() instanceof RequestResource) {
            final RequestResource requestResource = (RequestResource) message.getRequest();
            if (listener != null) {
                listener.resourceRequested(requestResource.getResource(), consumer);
            }

            ResourceProxy proxy = null;
            try {
                ResourceManager resourceManager = registry.getResourceManager(requestResource.getResource().getType());
                proxy = resourceManager.reserve(consumer, requestResource.getResource());
            } catch (ResourceException e) {
                e.printStackTrace();
            }
            if (proxy == null) {
                if (listener != null) {
                    listener.releaseRequestDenied();
                }
                consumer.requestDenied(message.getId());
            } else {
                if (listener != null) {
                    listener.releaseRequestAccepted();
                }
                try {
                    consumer.sendProxy(message.getId(), proxy);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
