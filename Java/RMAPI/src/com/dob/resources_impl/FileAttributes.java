package com.dob.resources_impl;

import com.dob.resources.ResourceAttributes;

public class FileAttributes implements ResourceAttributes {

    public String getName() {
        return "File";
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
        return true;
    }
}
