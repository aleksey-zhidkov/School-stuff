package com.dob.resources;

/**
 * Исключение управления ресурсами. Выбрасывается в случае возникновения
 * ошибок управления ресурсами
 */
public class ResourceException extends Exception {

    public ResourceException() {
    }

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceException(Throwable cause) {
        super(cause);
    }
}
