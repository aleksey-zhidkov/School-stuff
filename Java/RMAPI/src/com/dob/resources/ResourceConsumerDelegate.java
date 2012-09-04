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
 * Класс делегата потребителя ресурсов. Создаётся потребителем ресурсов и
 * отправляется менеджеру ресурсов.<br>
 * Представляет потребителя ресурсов в адресном пространстве менеджера ресурсов.
 *
 * @author Жидков Алексей
 */
public final class ResourceConsumerDelegate implements Serializable, ResourceConsumer {

    private final int appId;
    private final int priority;

    private transient IPCLink link;

    /**
     * Создаёт нового делегата потребителя ресурсов
     *
     * @param appId    идентификатор приложения потребителя ресурсов
     * @param priority приоритет приложения потребителя ресурсов
     */
    public ResourceConsumerDelegate(int appId, int priority) {
        this.appId = appId;
        this.priority = priority;
    }

    /**
     * Установка IPC-подключения
     *
     * @param link IPC-подключение
     */
    public void setLink(IPCLink link) {
        this.link = link;
    }

    /**
     * Запрос освобождения ресурса. Отправляет потребителю ресурсов.
     *
     * @param proxy       прокси ресурса, на освобождение которого запрашивается
     * @param requestData данные запроса (не используются)
     * @return <code>true</code>, если ресурс освобождён, иначе - <code>false</code>
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
     * Требование освобождения ресурса
     *
     * @param proxy прокси ресурса, который необходимо освободить
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
     * Оповещение об отзыве разрешения на доступ к ресурсу
     *
     * @param proxy прокси ресурса доступ к которому отозван
     */
    public void notifyReleased(ResourceProxy proxy) {
    }

    /**
     * Метод отпраки прокси запрошенного ресурса.
     *
     * @param requestId идентификатор запроса ресурса
     * @param proxy     прокси ресурса
     * @throws IOException, в случае ошибок IPC
     */
    public void sendProxy(Long requestId, ResourceProxy proxy) throws IOException {
        IPCResponse resp = new IPCResponse(requestId, new RequestAccepted(proxy));
        link.write(resp);
    }

    /**
     * Возвращяет идентификатор приложения потребителя ресурсов
     *
     * @return идентификатор приложения потребителя ресурсов
     */
    public int getAppId() {
        return appId;
    }

    /**
     * Возвращяет приоритет приложения потребителя ресурсов
     *
     * @return приоритет приложения потребителя ресурсов
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Отправка отказа в удволетворении запроса на доступ к ресурсу с
     * идентификатором <code>id</code>
     * @param id идентификатор запроса, в удволетворении которого отказано
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
