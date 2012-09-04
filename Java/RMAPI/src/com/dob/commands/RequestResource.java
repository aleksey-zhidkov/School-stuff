package com.dob.commands;

import com.dob.resources.ResourceDescriptor;

import java.io.Serializable;

/**
 * Команда запроса ресурса. Отправляется потребителем менеджеру ресурса
 * для запроса доступа к ресурсу. 
 *
 * @author Жидков Алексей
 */
public final class RequestResource implements Serializable {

    private final ResourceDescriptor resource;

    public RequestResource(ResourceDescriptor resource) {
        this.resource = resource;
    }

    public ResourceDescriptor getResource() {
        return resource;
    }
}
