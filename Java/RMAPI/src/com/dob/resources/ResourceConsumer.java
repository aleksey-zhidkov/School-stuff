/**
 * $Id$
 *
 * Copyright (c) 2009 Zodiac Interactive. All Rights Reserved.
 */
package com.dob.resources;

import org.davic.resources.ResourceClient;

public interface ResourceConsumer extends ResourceClient {

    /**
     * Возвращяет идентификатор приложения потребителя ресурсов
     *
     * @return идентификатор приложения потребителя ресурсов
     */
    int getAppId();

    /**
     * Возвращяет приоритет приложения потребителя ресурсов
     *
     * @return приоритет приложения потребителя ресурсов
     */
    int getPriority();

}
