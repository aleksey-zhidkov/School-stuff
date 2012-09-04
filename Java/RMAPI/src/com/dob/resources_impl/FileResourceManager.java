package com.dob.resources_impl;

import com.dob.resources.ResourceAttributes;
import com.dob.resources.ResourceDescriptor;
import com.dob.resources.ResourceException;
import com.dob.resources.ResourceConsumer;
import com.dob.resources.manager.AbstractResourceManager;
import org.davic.resources.ResourceProxy;

import java.io.File;
import java.io.FileNotFoundException;

public class FileResourceManager extends AbstractResourceManager {

    public ResourceAttributes getResourceAttributes() {
        return new FileAttributes();
    }

    protected ResourceProxy createProxy(final ResourceConsumer requestor, final ResourceDescriptor resource) throws ResourceException {
        final FileProxy proxy;
        try {
            proxy = new FileProxy(requestor, new File(resource.getDescriptor()), resource);
        } catch (FileNotFoundException e) {
            throw new ResourceException(e);
        }
        return proxy;
    }

}
