package com.dob.commands;

import org.davic.resources.ResourceProxy;

import java.io.Serializable;

/**
 *  оманда удволетворени€ запроса в доступе к ресурсу.
 * ќтправл€етс€ мнеджером ресурса потребителю, запросившему доступ к ресурсу.
 *
 * @author ∆идков јлексей
 */
public final class RequestAccepted implements Serializable {

    private final ResourceProxy proxy;

    /**
     * —оздаЄт новую команду об удволетворении доступа к ресурсу.
     *
     * @param proxy прокси ресурса, к которому предоставлен доступ
     */
    public RequestAccepted(ResourceProxy proxy) {
        this.proxy = proxy;
    }

    /**
     * ¬озвращ€ет прокси ресурса, к которому предоставлен доступ
     * @return прокси ресурса, к которому предоставлен доступ
     */
    public ResourceProxy getProxy() {
        return proxy;
    }
}
