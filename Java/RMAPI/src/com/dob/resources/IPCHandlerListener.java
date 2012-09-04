package com.dob.resources;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 01.05.2009
 * Time: 13:24:26
 * To change this template use File | Settings | File Templates.
 */

/**
 * Интерфейс слушателя событий IPC. Экземпляры классов реализующих данный интерфейс,
 * регестрируются в экземплярах классов реализующих интерфейс IPCHandler
 *
 * @author Жидков Алексей
 */
public interface IPCHandlerListener {

    /**
     * Событие открытия канала IPC
     */
    void connectorOpened();

    /**
     * События постуления запроса на подключения к каналу IPC
     */
    void newLink();

    /**
     * Событие получения делегата потребителя ресурсов
     *
     * @param conumer делегат потребителя ресурсов
     */
    void delegateRecieved(ResourceConsumer conumer);

    /**
     * Событие получения сообщения об освобождении ресурса
     */
    void releaseMessageReceived();

    /**
     * Событие получения сообщения о запросе доступа к ресурсу
     *
     * @param resource дескриптор ресурса, к которому запрашивается доступ
     * @param consumer делегат потребителя ресурсов, запрашивающий доступ
     */
    void resourceRequested(ResourceDescriptor resource, ResourceConsumer consumer);

    /**
     * Сообщение о получении отказа в запросе на освобождение ресурса
     */
    void releaseRequestDenied();

    /**
     * Сообщение о получении удволетворения запроса на освобождение ресурса
     */
    void releaseRequestAccepted();

}
