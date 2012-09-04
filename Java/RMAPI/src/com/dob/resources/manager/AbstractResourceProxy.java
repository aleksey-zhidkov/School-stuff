package com.dob.resources.manager;

import org.davic.resources.ResourceProxy;
import org.davic.resources.ResourceClient;
import com.dob.ipc.IPCLinkListener;
import com.dob.ipc.IPCLink;
import com.dob.commands.ReleaseResource;
import com.dob.resources.ResourceDescriptor;

import java.io.Serializable;

/**
 * Абстрактный класс прокси ресурса. Определяет данные, общие для всех ресурсов
 *
 * @author Жидков Алексей
 */
public abstract class AbstractResourceProxy implements ResourceProxy, IPCLinkListener, Serializable {

    private final transient ResourceClient client;
    private transient ResourceDescriptor descriptor;

    /**
     * Флаг подключённости к IPC
     */
    protected boolean hasLink = false;

    /**
     * Флаг закрытости ресурса
     */
    protected boolean closed = false;

    /**
     * Создаёт новое прокси ресурса
     *
     * @param client потребитель, для которого создаётя прокси
     * @param descriptor дескриптор ресурса
     */
    protected AbstractResourceProxy(ResourceClient client, ResourceDescriptor descriptor) {
        this.client = client;
        this.descriptor = descriptor;
    }

    /**
     * Возвращяет клиента потребителя ресурсов
     * @return клиент потребителя ресурсов
     */
    public ResourceClient getClient() {
        return client;
    }

    /**
     * Возвращяет дескриптор ресурса
     *
     * @return дескриптор ресурса
     */
    public ResourceDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * Установка подключения к IPC
     *
     * @param link подключение к IPC
     */
    public void setLink(IPCLink link) {
        link.addListener(this);
        hasLink = true;
    }

    /**
     * Событие о получении сообщения по IPC
     *
     * @param link подключение, по которому пришло сообщение
     * @param message сообщение
     * @return <code>true</code> если сообщение обработано, <code>false</code>  противном случае
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
     * Разрыв подключения к ресурсу
     */
    protected abstract void close();

    /**
     * Установка дескриптора ресурса
     *
     * @param resource дескриптор ресурса
     */
    void setDescriptor(ResourceDescriptor resource) {
        descriptor = resource;
    }
}
