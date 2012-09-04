package com.dob.commands;

import com.dob.resources.ResourceDescriptor;

import java.io.Serializable;

/**
 * Команда сообщения об освобождении ресурса. Отправляется потребителем ресурса
 * после освобождении ресурса
 *
 * @author Жидков Алексей
 */
public final class ResourceReleased implements Serializable {

    private final ResourceDescriptor descriptor;

    /**
     * Создаёт новую команду об освобождении ресурса
     * @param descriptor дескриптор освобождённого ресурса
     */
    public ResourceReleased(ResourceDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Возвращяет дескриптор освобождённого ресурса
     *
     * @return дескриптор освобождённого ресурса
     */
    public ResourceDescriptor getDescriptor() {
        return descriptor;
    }
}
