package ru.lexx.acsystem.interpretator.common.errors;

import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 09.02.2005
 * Time: 23:55:38
 */
public class UnexpectedLexemException extends UserErrorException {


    public UnexpectedLexemException(String message, int _line) {
        super(message, _line);
    }
}
