package com.dob.resources.manager;

import com.dob.resources_impl.FileResourceManager;
import com.dob.resources_impl.ServerSocketResourceManager;
import com.dob.resources.ResourceTypes;

import java.util.HashMap;
import java.util.Map;

public class ResourceManagerRegistryImpl implements ResourceManagerRegistry {

    private static final Map<String, ResourceManager> resourceManagers = new HashMap<String, ResourceManager>();

    static {
        resourceManagers.put(ResourceTypes.RESOURCE_FILE, new FileResourceManager());
        resourceManagers.put(ResourceTypes.RESOURCE_SERVER_SOCKET, new ServerSocketResourceManager());
    }

    public ResourceManager getResourceManager(String resourceName) {
        return resourceManagers.get(resourceName);
    }

}
