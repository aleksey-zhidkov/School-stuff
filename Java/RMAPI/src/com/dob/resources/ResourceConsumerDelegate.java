package com.dob.resources;

import com.dob.commands.ReleaseResource;
import com.dob.commands.RequestAccepted;
import com.dob.commands.RequestDenied;
import com.dob.commands.RequestRelease;
import com.dob.ipc.IPCLink;
import com.dob.ipc.IPCResponse;
import com.dob.resources.manager.AbstractResourceProxy;
import org.davic.resources.ResourceProxy;

import java.io.IOException;
import java.io.Serializable;

/**
 * ����� �������� ����������� ��������. �������� ������������ �������� �
 * ������������ ��������� ��������.<br>
 * ������������ ����������� �������� � �������� ������������ ��������� ��������.
 *
 * @author ������ �������
 */
public final class ResourceConsumerDelegate implements Serializable, ResourceConsumer {

    private final int appId;
    private final int priority;

    private transient IPCLink link;

    /**
     * ������ ������ �������� ����������� ��������
     *
     * @param appId    ������������� ���������� ����������� ��������
     * @param priority ��������� ���������� ����������� ��������
     */
    public ResourceConsumerDelegate(int appId, int priority) {
        this.appId = appId;
        this.priority = priority;
    }

    /**
     * ��������� IPC-�����������
     *
     * @param link IPC-�����������
     */
    public void setLink(IPCLink link) {
        this.link = link;
    }

    /**
     * ������ ������������ �������. ���������� ����������� ��������.
     *
     * @param proxy       ������ �������, �� ������������ �������� �������������
     * @param requestData ������ ������� (�� ������������)
     * @return <code>true</code>, ���� ������ ���������, ����� - <code>false</code>
     */
    public synchronized boolean requestRelease(ResourceProxy proxy, Object requestData) {
        RequestRelease releaseRequest = new RequestRelease(((AbstractResourceProxy) proxy).getDescriptor());
        try {
            releaseRequest = (RequestRelease) link.sendRequest(releaseRequest, 3000);
            if (releaseRequest == null) {
                release(proxy);
                return true;
            }
            System.out.println("Request release answer recieved: " + releaseRequest.getAnswer());
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
        return releaseRequest.getAnswer();
    }

    /**
     * ���������� ������������ �������
     *
     * @param proxy ������ �������, ������� ���������� ����������
     */
    public void release(ResourceProxy proxy) {
        try {
            final ReleaseResource message = new ReleaseResource(((AbstractResourceProxy) proxy).getDescriptor());
            link.sendRequest(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���������� �� ������ ���������� �� ������ � �������
     *
     * @param proxy ������ ������� ������ � �������� �������
     */
    public void notifyReleased(ResourceProxy proxy) {
    }

    /**
     * ����� ������� ������ ������������ �������.
     *
     * @param requestId ������������� ������� �������
     * @param proxy     ������ �������
     * @throws IOException, � ������ ������ IPC
     */
    public void sendProxy(Long requestId, ResourceProxy proxy) throws IOException {
        IPCResponse resp = new IPCResponse(requestId, new RequestAccepted(proxy));
        link.write(resp);
    }

    /**
     * ���������� ������������� ���������� ����������� ��������
     *
     * @return ������������� ���������� ����������� ��������
     */
    public int getAppId() {
        return appId;
    }

    /**
     * ���������� ��������� ���������� ����������� ��������
     *
     * @return ��������� ���������� ����������� ��������
     */
    public int getPriority() {
        return priority;
    }

    /**
     * �������� ������ � �������������� ������� �� ������ � ������� �
     * ��������������� <code>id</code>
     * @param id ������������� �������, � �������������� �������� ��������
     */
    public void requestDenied(long id) {
        try {
            IPCResponse response = new IPCResponse(id, new RequestDenied());
            link.write(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
