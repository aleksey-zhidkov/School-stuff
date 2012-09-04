package com.dob.ipc;

public interface IPCLinkListener {

    boolean newMessage(IPCLink link, Object message);

}
