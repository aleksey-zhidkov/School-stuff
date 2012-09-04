package com.dob.resources_impl;

import com.dob.resources.manager.AbstractResourceProxy;
import com.dob.resources.ResourceDescriptor;

import java.net.*;
import java.io.IOException;

import org.davic.resources.ResourceClient;

public class ServerSocketProxy extends AbstractResourceProxy {

    private ServerSocket delegate;

    protected ServerSocketProxy(ResourceClient client, ResourceDescriptor descriptor) {
        super(client, descriptor);
    }

    protected void close() {
        try {
            System.out.println("Socket closed");
            delegate.close();
        } catch (IOException ignore) {
        }
    }

    public ServerSocket getSocket() throws IOException {
        if (delegate == null) {
            delegate = new ServerSocket(Integer.parseInt(getDescriptor().getDescriptor()));
        }
        return delegate;
    }
}
