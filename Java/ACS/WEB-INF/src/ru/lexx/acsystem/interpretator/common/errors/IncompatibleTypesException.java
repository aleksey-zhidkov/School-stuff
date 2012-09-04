package ru.lexx.acsystem.interpretator.common.errors;

import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 09.02.2005
 * Time: 23:59:12
 */
public class IncompatibleTypesException extends UserErrorException {

    public IncompatibleTypesException(String message, int _line) {
        super(message, _line);
    }
}
