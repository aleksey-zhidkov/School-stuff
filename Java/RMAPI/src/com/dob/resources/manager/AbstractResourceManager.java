package com.dob.resources.manager;

import com.dob.resources.ResourceConsumerDelegate;
import com.dob.resources.ResourceDescriptor;
import com.dob.resources.ResourceException;
import com.dob.resources.ResourceConsumer;
import org.davic.resources.ResourceProxy;
import org.davic.resources.ResourceStatusListener;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * ����� ������������ ��������� ��������. ���������� ����� ��������� ������,
 * ����������� ��� ���������� ��������� � �������� ���������� ��������� �� ���������.
 * ���������� ���������� ��������� �� ��������� �������� ���������:
 * <ol>
 * <li> ���� ������ �������� ���������� - ��������� ������
 * <li> ���� ������ �������� - ��������� ������
 * <li> ���� ������ ����� ������������ � ������� ����������� - ��������
 * � �������� ����������� � ��������� ������
 * <li> ���� ������ ����� ������������ � ������� ����������� - ���������
 * ������ �� ������������ ������� � � ����������� �� ������� ���������
 * ��� �������� � �������
 * </ol>
 *
 * @author ������ �������
 */
public abstract class AbstractResourceManager implements ResourceManager {

    private final Map<ResourceDescriptor, ResourceConsumerDelegate> resourceOweners = new HashMap<ResourceDescriptor, ResourceConsumerDelegate>();

    private final Map<ResourceDescriptor, ResourceProxy> proxies = new HashMap<ResourceDescriptor, ResourceProxy>();
    private final List<ResourceStatusListener> resourceStatusListeners = new ArrayList<ResourceStatusListener>();
    private boolean isTerminated = false;

    /**
     * ����������� <code>consumer</code> ��� �������� ��������� <code>resource</code>
     * @param resource ���������� �������
     * @param consumer ����������� �������
     */
    protected final void setOwener(ResourceDescriptor resource, ResourceConsumerDelegate consumer) {
        resourceOweners.put(resource, consumer);
    }

    /**
     * ���������� �������� ��������� <code>resource</code>
     *
     * @param resource ���������� �������
     * @return ��������� �������
     */
    protected final ResourceConsumer getOwener(ResourceDescriptor resource) {
        return resourceOweners.get(resource);
    }

    /**
     * ���������� ������ ��� �������
     *
     * @param resource ���������� �������
     * @return ������ �������
     */
    protected ResourceProxy getProxy(ResourceDescriptor resource) {
        return proxies.get(resource);
    }

    /**
     * ���������� �������������� �� ������
     *
     * @param resource ���������� �������
     * @return <code>true</code>, ���� ������ ��������������, � ��������� ������ - <code>false</code>
     */
    public boolean isReserved(ResourceDescriptor resource) {
        return getOwener(resource) != null;
    }

    /**
     * ����� ���������� �������.
     *
     * @param requestor ����������� �������
     * @param resource ���������� �������
     * @return ������ ������� ��� <code>null</code>, ���� ������ �� ����� ���� ��������������
     * @throws ResourceException
     */
    public ResourceProxy reserve(ResourceConsumer requestor, ResourceDescriptor resource) throws ResourceException {
        ResourceProxy proxy = proxies.get(resource);

        if (getResourceAttributes().isReusble()) {
            if (proxy == null) {
                proxy = createProxy(requestor, resource);
                proxies.put(resource, proxy);
            } else {
                reuse(proxy);
            }
        } else {
            proxy = createProxy(requestor, resource);
        }

        ResourceConsumer owener = getOwener(resource);
        if (owener == null) {
            setOwener(resource, (ResourceConsumerDelegate) requestor);
            return proxy;
        }

        if (requestor == owener) {
            return proxy;
        }

        return resolveConflict(requestor, resource, proxy, owener);
    }

    private ResourceProxy resolveConflict(ResourceConsumer requestor, ResourceDescriptor resource, ResourceProxy proxy, ResourceConsumer owener) {
        if (owener.getPriority() >= requestor.getPriority()) {
            if (owener.requestRelease(proxy, null)) {
                setOwener(resource, (ResourceConsumerDelegate) requestor);
                owener.release(proxy);
                return proxy;
            } else {
                return null;
            }
        } else {
            release(resource);
            setOwener(resource, (ResourceConsumerDelegate) requestor);
            return proxy;
        }
    }

    /**
     * ����� ���������� ������ ������� � �����������������
     *
     * @param proxy ������ �������
     */
    protected void reuse(ResourceProxy proxy) {
        
    }

    /**
     * ���������� ������������ �������
     * @param resource ���������� �������
     */
    public void release(ResourceDescriptor resource) {
        ResourceConsumer owener = getOwener(resource);
        owener.release(getProxy(resource));
        setOwener(resource, null);
    }

    /**
     * ���������� �� ������������ �������
     *
     * @param resource ���������� �������
     */
    public void notifyReleased(ResourceDescriptor resource) {
        setOwener(resource, null);
    }

    /**
     * ������ ������ �������
     *
     * @param requestor ����������� ������� ��� �������� �������� ������
     * @param resource ������ ��� �������� �������� ������
     * @return ������ �������
     * @throws ResourceException
     */
    protected abstract ResourceProxy createProxy(ResourceConsumer requestor, ResourceDescriptor resource) throws ResourceException;

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
        isTerminated = true;
    }

    /**
     * ���������� ��������� �������� �������
     *
     * @return <code>true</code>, ���� �������� ���������� �
     * <code>false</code>, ���� �������� ����� � ������.
     */
    public boolean isTerminated() {
        return isTerminated;
    }
}
