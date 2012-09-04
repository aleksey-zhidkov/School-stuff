package com.dob.commands;

import com.dob.resources.ResourceDescriptor;

import java.io.Serializable;

/**
 * Команда освобождения ресурса. Отправляется как менеджером, так и
 * потребителем ресурса.
 *
 * @author Жидков Алексей
 */
public final class ReleaseResource implements Serializable {

    private final ResourceDescriptor descriptor;

    /**
     * Создаёт новую команду об освобождении ресурса
     * @param descriptor дескриптор освобождаемого ресурса
     */
    public ReleaseResource(ResourceDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Возвращяет дескриптор освобождаемого ресурса
     * @return дескриптор освобождаемого ресурса
     */
    public ResourceDescriptor getDescriptor() {
        return descriptor;
    }
}
