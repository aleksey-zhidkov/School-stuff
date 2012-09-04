package com.dob.resources_impl;

import com.dob.resources.ResourceAttributes;

public class ServerSocketAttributes implements ResourceAttributes {
    public String getName() {
        return "ServerSocket";
    }

    public long getTotalQuantity() {
        return -1;
    }

    public boolean isDisposable() {
        return false;
    }

    public boolean isReservable() {
        return true;
    }

    public boolean isReusble() {
        return false;
    }
}
