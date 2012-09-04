package org.davic.resources;

public interface ResourceServer {

    void addResourceStatusEventListener(ResourceStatusListener listener);
    void removeResourceStatusEventListener(ResourceStatusListener listener);
    
}
