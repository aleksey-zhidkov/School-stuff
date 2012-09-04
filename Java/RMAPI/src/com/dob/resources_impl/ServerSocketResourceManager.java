package com.dob.resources_impl;

import com.dob.resources.manager.AbstractResourceManager;
import com.dob.resources.ResourceConsumer;
import com.dob.resources.ResourceDescriptor;
import com.dob.resources.ResourceException;
import com.dob.resources.ResourceAttributes;
import org.davic.resources.ResourceProxy;

public class ServerSocketResourceManager extends AbstractResourceManager {

    private static final ServerSocketAttributes attributes = new ServerSocketAttributes();

    protected void reuse(ResourceProxy proxy) {
    }

    protected ResourceProxy createProxy(ResourceConsumer requestor, ResourceDescriptor resource) throws ResourceException {
        final ServerSocketProxy socketProxy = new ServerSocketProxy(requestor, resource);
        return socketProxy;
    }

    public ResourceAttributes getResourceAttributes() {
        return attributes;
    }
}
