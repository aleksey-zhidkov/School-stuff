package com.dob.resources.manager;

import com.dob.resources.ResourceConsumer;

/**
 * Класс менеджеров ресурсов, позволяющий их очисту.
 *
 * @author Жидков Алексей
 */
public interface ReclamationManager extends ResourceManager {

    /**
     * Определение чистоты ресурсов потребителя
     *
     * @param resourceConsumer потребитель ресурсов
     * @return <code>true</code> если ресурсы выделенные потребителю очищены,
     * <code>false</code> иначе
     */
    boolean isCleared(ResourceConsumer resourceConsumer);

    /**
     * Очистка ресурсов выделенных потребителю
     *
     * @param resourceConsumer потребитель ресурсов
     */
    void releaseResources(ResourceConsumer resourceConsumer);

}
