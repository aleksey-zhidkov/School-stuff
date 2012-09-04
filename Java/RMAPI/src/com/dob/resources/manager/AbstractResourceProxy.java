package com.dob.resources.manager;

import org.davic.resources.ResourceProxy;
import org.davic.resources.ResourceClient;
import com.dob.ipc.IPCLinkListener;
import com.dob.ipc.IPCLink;
import com.dob.commands.ReleaseResource;
import com.dob.resources.ResourceDescriptor;

import java.io.Serializable;

/**
 * ����������� ����� ������ �������. ���������� ������, ����� ��� ���� ��������
 *
 * @author ������ �������
 */
public abstract class AbstractResourceProxy implements ResourceProxy, IPCLinkListener, Serializable {

    private final transient ResourceClient client;
    private transient ResourceDescriptor descriptor;

    /**
     * ���� �������������� � IPC
     */
    protected boolean hasLink = false;

    /**
     * ���� ���������� �������
     */
    protected boolean closed = false;

    /**
     * ������ ����� ������ �������
     *
     * @param client �����������, ��� �������� ������� ������
     * @param descriptor ���������� �������
     */
    protected AbstractResourceProxy(ResourceClient client, ResourceDescriptor descriptor) {
        this.client = client;
        this.descriptor = descriptor;
    }

    /**
     * ���������� ������� ����������� ��������
     * @return ������ ����������� ��������
     */
    public ResourceClient getClient() {
        return client;
    }

    /**
     * ���������� ���������� �������
     *
     * @return ���������� �������
     */
    public ResourceDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * ��������� ����������� � IPC
     *
     * @param link ����������� � IPC
     */
    public void setLink(IPCLink link) {
        link.addListener(this);
        hasLink = true;
    }

    /**
     * ������� � ��������� ��������� �� IPC
     *
     * @param link �����������, �� �������� ������ ���������
     * @param message ���������
     * @return <code>true</code> ���� ��������� ����������, <code>false</code>  ��������� ������
     */
    public boolean newMessage(IPCLink link, Object message) {
        if (message instanceof ReleaseResource) {
            close();
            closed = true;
            return true;
        }
        return false;
    }

    /**
     * ������ ����������� � �������
     */
    protected abstract void close();

    /**
     * ��������� ����������� �������
     *
     * @param resource ���������� �������
     */
    void setDescriptor(ResourceDescriptor resource) {
        descriptor = resource;
    }
}
