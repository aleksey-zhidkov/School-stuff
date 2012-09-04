package com.dob.resources;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 24.05.2009
 * Time: 8:28:15
 * To change this template use File | Settings | File Templates.
 */

/**
 * Интерфейс классов работы с различными механизмами IPC.
 * Классы его реализующие должны оповещать слушателя заданного
 * соответвующим методом о событиях IPC.
 *
 * @author Жидков Алексей
 */
public interface IPCHandler {

    /**
     * Метод установки слушателя событий IPC
     * @param listener слушатель событий IPC
     */
    void setListener(IPCHandlerListener listener);
}
