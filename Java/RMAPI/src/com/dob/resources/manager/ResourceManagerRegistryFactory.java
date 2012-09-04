package com.dob.resources.manager;

public class ResourceManagerRegistryFactory {
    private static final ResourceManagerRegistryImpl serverRegistry = new ResourceManagerRegistryImpl();
    private static final ResourceManagerRegistryDelegate clientRegistry = new ResourceManagerRegistryDelegate(serverRegistry);

    public static ResourceManagerRegistry getRegistry() {
        String registryClass = System.getProperty("resource_manager_regestry");
        if ("client".equals(registryClass)) {
            return clientRegistry;
        }
        return serverRegistry;
    }

}
