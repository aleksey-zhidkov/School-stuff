/**
 * $Id$
 *
 * Copyright (c) 2009 Zodiac Interactive. All Rights Reserved.
 */
package com.dob.resources.manager;

import com.dob.commands.ReleaseResource;
import com.dob.commands.RequestAccepted;
import com.dob.commands.RequestRelease;
import com.dob.commands.RequestResource;
import com.dob.commands.ResourceReleased;
import com.dob.ipc.IPCConnector;
import com.dob.ipc.IPCConnectorListener;
import com.dob.ipc.IPCLink;
import com.dob.ipc.IPCLinkListener;
import com.dob.ipc.IPCRequest;
import com.dob.ipc.IPCResponse;
import com.dob.resources.ResourceAttributes;
import com.dob.resources.ResourceConsumer;
import com.dob.resources.ResourceConsumerDelegate;
import com.dob.resources.ResourceDescriptor;
import com.dob.resources.ResourceException;
import org.davic.resources.ResourceProxy;
import org.davic.resources.ResourceStatusListener;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * ������� ���������� ��������. ������������ ��������� �������� �
 * �������� ������������ ����������� ��������.
 * ���������� ��������� ������ � ��������� ����� ��� ���� ��������
 */
public class ResourceManagerDelegate implements IPCConnectorListener, IPCLinkListener, ResourceManager {

    private final Map<ResourceDescriptor, ResourceConsumer> consumers = new HashMap<ResourceDescriptor, ResourceConsumer>();
    private final Map<ResourceDescriptor, ResourceProxy> proxies = new HashMap<ResourceDescriptor, ResourceProxy>();

    private final ResourceAttributes attributes;

    private IPCLink link;

    private boolean isDelegateSended = false;
    private final List<ResourceStatusListener> resourceStatusListeners = new ArrayList<ResourceStatusListener>();
    private boolean isTerminated = false;

    /**
     * ������ ������� ��������� �������� ��� ���������� ��������,
     * ������������ <code>attributes</code>
     *
     * @param attributes ��������� �������, ��� �������� �������� ��������
     */
    public ResourceManagerDelegate(ResourceAttributes attributes) {
        this.attributes = attributes;
    }

    /**
     * ���������� �������� ����������� ��������
     *
     * @return �������� ����������� ��������
     */
    public ResourceAttributes getResourceAttributes() {
        return attributes;
    }

    /**
     * �� �������������� �����. ����������� <code>UnsupportedOperationException</code>
     *
     * @param resource
     * @return
     */
    public boolean isReserved(ResourceDescriptor resource) {
        throw new UnsupportedOperationException();
    }

    /**
     * ���������� �������. ��� ���������� ���������� ������ ��������� ��������.
     *
     * @param requestor ����������� �������
     * @param resource  ���������� �������
     * @return ������ ������� ��� <code>null</code>, ���� ������ �� ����� ���� ��������������
     * @throws ResourceException
     */
    public ResourceProxy reserve(ResourceConsumer requestor, ResourceDescriptor resource) throws ResourceException {
        RequestResource request = new RequestResource(resource);
        AbstractResourceProxy res = null;
        try {
            ensureDelegateSended(requestor);
            System.out.println("Send resource request");
            Serializable answer = link.sendRequest(request, 3000);
            System.out.println("Response recieved");
            if (answer instanceof RequestAccepted) {
                res = (AbstractResourceProxy) ((RequestAccepted) answer).getProxy();
                res.setLink(link);
                res.setDescriptor(resource);
                consumers.put(resource, requestor);
                proxies.put(resource, res);
                System.out.println("Accept reeived: " + consumers + ", " + proxies);
            }
        } catch (IOException e) {
            throw new ResourceException(e);
        }
        System.out.println("Return proxy: " + res);
        return res;
    }

    /**
     * ������������ �������. ��� ������������ ���������� ������ ��������� ��������.
     *
     * @param resource ���������� �������
     * @throws ResourceException
     */
    public void release(ResourceDescriptor resource) throws ResourceException {
        try {
            ensureDelegateSended(null);
            link.write(new ReleaseResource(resource));
        } catch (IOException e) {
            throw new ResourceException(e);
        }
    }

    /**
     * ��������� �� ������������ �������. ��� ��������� �� ������������ ���������� ������ ��������� ��������.
     *
     * @param resource ���������� �������
     * @throws ResourceException
     */
    public void notifyReleased(ResourceDescriptor resource) throws ResourceException {
        try {
            ensureDelegateSended(null);
            link.write(new ResourceReleased(resource));
        } catch (IOException e) {
            throw new ResourceException(e);
        }
    }

    /**
     * �� ����������
     *
     * @param link IPC-�����������
     */
    public void newLink(IPCLink link) {
    }

    /**
     * ��������� ������ ���������. ������������ ������ ��������� ���� <code>IPCRequest</code>
     *
     * @param link    IPC-����������� �� �������� ������ ���������
     * @param message ���������
     * @return <code>true</code> ���� ��������� ����������, <code>false</code> �����
     */
    public boolean newMessage(IPCLink link, Object message) {
        System.out.println("New message: " + message);
        if (message instanceof IPCRequest) {
            handleRequest(link, (IPCRequest) message);
        }
        return false;
    }

    private void handleRequest(IPCLink link, IPCRequest ipcRequest) {
        System.out.println("IPCRequest: " + ipcRequest);
        if (ipcRequest.getRequest() instanceof RequestRelease) {
            final RequestRelease reqMessage = (RequestRelease) ipcRequest.getRequest();
            System.out.println("Handle release request: " + reqMessage + ", " + consumers + ", " + proxies);
            reqMessage.setAnswer(consumers.get(reqMessage.getDescriptor()).requestRelease(proxies.get(reqMessage.getDescriptor()), null));
            IPCResponse res = new IPCResponse(ipcRequest.getId(), reqMessage);
            try {
                link.write(res);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (ipcRequest.getRequest() instanceof ReleaseResource) {
            final ResourceDescriptor descriptor = ((ReleaseResource) ipcRequest.getRequest()).getDescriptor();
            ResourceConsumer consumer = consumers.get(descriptor);
            System.out.println("Releasing resource");
            consumer.release(proxies.get(descriptor));
            System.out.println("Resource released");
            IPCResponse res = new IPCResponse(ipcRequest.getId(), new ResourceReleased(descriptor));
            try {
                link.write(res);
                System.out.println("Response sended");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void ensureDelegateSended(ResourceConsumer resourceConsumer) throws IOException {
        if (!isDelegateSended) {
            if (resourceConsumer == null) {
                throw new IllegalStateException("Delegate isn't sended");
            }
            IPCConnector conn = new IPCConnector(resourceConsumer.getAppId(), this);
            while (link == null) {
                try {
                    link = conn.open(0);
                } catch (IOException ignore) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            link.addListener(this);
            ResourceConsumerDelegate delegate = new ResourceConsumerDelegate(resourceConsumer.getAppId(),
                    resourceConsumer.getPriority());
            link.write(delegate);
            isDelegateSended = true;
        }
    }

    /**
     * ���������� ��������� � ������ ���������� ��������� ��������
     *
     * @param listener ���������
     */
    public void addResourceStatusEventListener(ResourceStatusListener listener) {
        if (resourceStatusListeners.contains(listener)) {
            return;
        }

        resourceStatusListeners.add(listener);
    }

    /**
     * �������� ��������� �� ������ ���������� ��������� ��������
     *
     * @param listener ���������
     */
    public void removeResourceStatusEventListener(ResourceStatusListener listener) {
        resourceStatusListeners.remove(listener);
    }

    /**
     * ���������� ������ �������� �������
     */
    public void terminate() {
        try {
            link.close();
        } catch (IOException ignore) {
        }
        isTerminated = true;
    }

    /**
     * ���������� ��������� �������� �������
     *
     * @return <code>true</code>, ���� �������� ���������� �
     *         <code>false</code>, ���� �������� ����� � ������.
     */
    public boolean isTerminated() {
        return isTerminated;
    }
}
