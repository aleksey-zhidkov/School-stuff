/**
 * $Id$
 *
 * Copyright (c) 2009 Zodiac Interactive. All Rights Reserved.
 */
package com.dob.ipc;

import java.io.Serializable;

public class IPCRequest implements Serializable {

    private final long id;
    private final Serializable request;

    public IPCRequest(long id, Serializable request) {
        this.id = id;
        this.request = request;
    }

    public long getId() {
        return id;
    }

    public Serializable getRequest() {
        return request;
    }

    public String toString() {
        return "IPCRequest: [" + id + ", " + request + "]";
    }
}
