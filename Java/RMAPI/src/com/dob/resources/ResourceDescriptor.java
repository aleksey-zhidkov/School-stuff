/**
 * $Id$
 *
 * Copyright (c) 2009 Zodiac Interactive. All Rights Reserved.
 */
package com.dob.resources;

import java.io.Serializable;

/**
 * Класс дескрипотра ресурса. Используется для его идентификации
 *
 * @author Жидков Алексей
 */
public class ResourceDescriptor implements Serializable {

    private final String descriptor;
    private final String type;

    /**
     * Создаёт новый дескриптор ресурса
     *
     * @param descriptor описание ресурса
     * @param type тип ресурса
     */
    public ResourceDescriptor(String descriptor, String type) {
        this.descriptor = descriptor;
        this.type = type;
    }

    /**
     * Возвращяет описание ресурса
     *
     * @return описание ресурса
     */
    public String getDescriptor() {
        return descriptor;
    }

    /**
     * Возвращяет тип ресурса
     *
     * @return тип ресурса
     */
    public String getType() {
        return type;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceDescriptor that = (ResourceDescriptor)o;

        if (!descriptor.equals(that.descriptor)) return false;
        if (!type.equals(that.type)) return false;

        return true;
    }

    public int hashCode() {
        int result = descriptor.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
