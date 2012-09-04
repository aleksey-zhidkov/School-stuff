/**
 * $Id$
 *
 * Copyright (c) 2009 Zodiac Interactive. All Rights Reserved.
 */
package com.dob.ipc;

import java.io.Serializable;

public class IPCResponse implements Serializable {

    private final long id;
    private final Serializable message;

    public IPCResponse(long id, Serializable message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public Serializable getMessage() {
        return message;
    }


    public String toString() {
        return "IPCResponse: [" + id + ", " + message + "]";
    }

}
