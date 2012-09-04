package com.dob.resources.manager;

import java.util.Map;
import java.util.HashMap;

public class ResourceManagerRegistryDelegate implements ResourceManagerRegistry {

    private ResourceManagerRegistry resourceManagerRegistry;

    private final Map<String, ResourceManager> resourceManagers = new HashMap<String, ResourceManager>();

    public ResourceManagerRegistryDelegate(ResourceManagerRegistry resourceManagerRegistry) {
        this.resourceManagerRegistry = resourceManagerRegistry;
    }

    public ResourceManager getResourceManager(String resourceName) {
        ResourceManager res = resourceManagers.get(resourceName);

        if (res == null) {
            res = resourceManagerRegistry.getResourceManager(resourceName);
            res = new ResourceManagerDelegate(res.getResourceAttributes());
            resourceManagers.put(resourceName, res);
        }

        return res;
    }
}
