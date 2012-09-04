package ru.lexx.acsystem.interpretator.common.errors;

import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 10.02.2005
 * Time: 23:20:48
 */
public class NoSuchFunctionException extends UserErrorException {
    public NoSuchFunctionException(String message, int _line) {
        super(message, _line);
    }
}
