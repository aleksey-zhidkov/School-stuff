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
 * Класс абстрактного менеджера ресурсов. Определяет общие структуры данных,
 * необходимые для управления ресурсами и алгоритм управления ресурсами по умолчанию.
 * Алгоритмом управления ресурсами по умолчанию является следующий:
 * <ol>
 * <li> если ресурс запрошен владельцем - разрешить доступ
 * <li> если ресурс свободен - разрешить доступ
 * <li> если ресурс занят потребителем с меньшим приоритетом - отобрать
 * у текущего потребителя и разрешить доступ
 * <li> если ресурс занят потребителем с большим приоритетом - отправить
 * запрос на освобождение ресурса и в зависимости от отверта разершить
 * или отказать в доступе
 * </ol>
 *
 * @author Жидков Алексей
 */
public abstract class AbstractResourceManager implements ResourceManager {

    private final Map<ResourceDescriptor, ResourceConsumerDelegate> resourceOweners = new HashMap<ResourceDescriptor, ResourceConsumerDelegate>();

    private final Map<ResourceDescriptor, ResourceProxy> proxies = new HashMap<ResourceDescriptor, ResourceProxy>();
    private final List<ResourceStatusListener> resourceStatusListeners = new ArrayList<ResourceStatusListener>();
    private boolean isTerminated = false;

    /**
     * Определение <code>consumer</code> как текущего владельца <code>resource</code>
     * @param resource дескриптор ресурса
     * @param consumer потребитель ресурса
     */
    protected final void setOwener(ResourceDescriptor resource, ResourceConsumerDelegate consumer) {
        resourceOweners.put(resource, consumer);
    }

    /**
     * Возвращяет текущего владельца <code>resource</code>
     *
     * @param resource дескриптор ресурса
     * @return владельца ресурса
     */
    protected final ResourceConsumer getOwener(ResourceDescriptor resource) {
        return resourceOweners.get(resource);
    }

    /**
     * Возвращяет прокси для ресурса
     *
     * @param resource дескриптор ресурса
     * @return прокси ресурса
     */
    protected ResourceProxy getProxy(ResourceDescriptor resource) {
        return proxies.get(resource);
    }

    /**
     * Определяет зарезервирован ли ресурс
     *
     * @param resource дескриптор ресурса
     * @return <code>true</code>, если ресурс зарезервирован, в противном случае - <code>false</code>
     */
    public boolean isReserved(ResourceDescriptor resource) {
        return getOwener(resource) != null;
    }

    /**
     * Метод резервации ресурса.
     *
     * @param requestor потребитель ресурса
     * @param resource дескриптор ресурса
     * @return прокси ресурса или <code>null</code>, если ресурс не может быть зарезервирован
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
     * Метод подкотовки прокси ресурса к переиспользованию
     *
     * @param proxy прокси ресурса
     */
    protected void reuse(ResourceProxy proxy) {
        
    }

    /**
     * Требование освобождения ресурса
     * @param resource дескриптор ресурса
     */
    public void release(ResourceDescriptor resource) {
        ResourceConsumer owener = getOwener(resource);
        owener.release(getProxy(resource));
        setOwener(resource, null);
    }

    /**
     * Оповещение об освобождении ресурса
     *
     * @param resource дескриптор ресурса
     */
    public void notifyReleased(ResourceDescriptor resource) {
        setOwener(resource, null);
    }

    /**
     * Создаёт прокси ресурса
     *
     * @param requestor потребитель ресурса для которого создаётся прокси
     * @param resource ресурс для которого создаётся прокси
     * @return прокси ресурса
     * @throws ResourceException
     */
    protected abstract ResourceProxy createProxy(ResourceConsumer requestor, ResourceDescriptor resource) throws ResourceException;

    /**
     * Добавления слушателя к списку слушателей состояний ресурсов
     *
     * @param listener слушатель
     */
    public void addResourceStatusEventListener(ResourceStatusListener listener) {
        if (resourceStatusListeners.contains(listener)) {
            return;
        }

        resourceStatusListeners.add(listener);
    }

    /**
     * Удаление слушателя из списка слушателей состояний ресурсов
     *
     * @param listener слушатель
     */
    public void removeResourceStatusEventListener(ResourceStatusListener listener) {
        resourceStatusListeners.remove(listener);
    }

    /**
     * Завершение работы менджера ресурса
     */
    public void terminate() {
        isTerminated = true;
    }

    /**
     * Возвращяет состояние менджера ресурса
     *
     * @return <code>true</code>, если менеджер остановлен и
     * <code>false</code>, если менеджер готов к работе.
     */
    public boolean isTerminated() {
        return isTerminated;
    }
}
