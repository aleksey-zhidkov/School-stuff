package com.dob.commands;

import com.dob.resources.ResourceDescriptor;

import java.io.Serializable;

/**
 * Команда запроса освобождения ресурса. Отправляется менеджером ресурса
 * потребителю, владющему ресурсом в данный момент.
 *
 * @author Жидков Алексей
 */
public final class RequestRelease implements Serializable {

    private final ResourceDescriptor descriptor;
    private boolean answer = true;

    /**
     * Создаёт новую команду апроса освобождения ресурса.
     *
     * @param descriptor дескриптор ресурса, который необходимо освободить
     */
    public RequestRelease(ResourceDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    /**
     * Возвращяет дескриптор ресурса, который необходимо освободить
     *
     * @return дескриптор ресурса, который необходимо освободить
     */
    public ResourceDescriptor getDescriptor() {
        return descriptor;
    }

    /**
     * Возвращяет ответ владельца ресурса
     *
     * @return <code>true</code>, если владелец решил освободить и освободил ресурс
     * и <code>false</code> в противном случае
     */
    public boolean getAnswer() {
        return answer;
    }

    /**
     * Установка решения потребителя ресурса.
     *
     * @param answer решение потребителя ресурса. <code>true</code>, если ресурс
     * освобождён, иначе - <code>false</code>
     */
    public void setAnswer(boolean answer) {
        this.answer = answer;
    }
}
