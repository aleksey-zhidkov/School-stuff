package org.davic.resources;

public interface ResourceClient {
    
    boolean requestRelease(ResourceProxy proxy, Object requestData);
    void release(ResourceProxy proxy);
    void notifyReleased(ResourceProxy proxy);

}
