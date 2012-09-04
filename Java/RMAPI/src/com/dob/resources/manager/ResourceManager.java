package com.dob.resources.manager;

import com.dob.resources.ResourceAttributes;
import com.dob.resources.ResourceConsumer;
import com.dob.resources.ResourceDescriptor;
import com.dob.resources.ResourceException;
import org.davic.resources.ResourceProxy;
import org.davic.resources.ResourceServer;

public interface ResourceManager extends ResourceServer {

    ResourceAttributes getResourceAttributes();

    boolean isReserved(ResourceDescriptor resource);

    ResourceProxy reserve(ResourceConsumer requestor, ResourceDescriptor resource) throws ResourceException;

    void release(ResourceDescriptor resource) throws ResourceException;

    void notifyReleased(ResourceDescriptor resource) throws ResourceException;

    void terminate();

    boolean isTerminated();

}
