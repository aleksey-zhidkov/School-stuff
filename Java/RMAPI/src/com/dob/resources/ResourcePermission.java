package com.dob.resources;

import java.security.BasicPermission;

public class ResourcePermission extends BasicPermission {
    
    public ResourcePermission(String name) {
        super(name);
    }

    public ResourcePermission(String name, String actions) {
        super(name, actions);
    }

}
