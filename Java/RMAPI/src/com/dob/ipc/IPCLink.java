package com.dob.ipc;

import java.io.IOException;
import java.io.Serializable;

public interface IPCLink {

    void write(Serializable message) throws IOException;

    Serializable sendRequest(Serializable request) throws IOException;

    Serializable sendRequest(Serializable request, long timeOut) throws IOException;

    void close() throws IOException;

    boolean isClosed();

    void addListener(IPCLinkListener listener);

    void removeListner(IPCLinkListener listener);
}
