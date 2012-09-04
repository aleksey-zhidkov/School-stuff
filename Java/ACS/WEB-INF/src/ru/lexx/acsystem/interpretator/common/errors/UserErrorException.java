package ru.lexx.acsystem.interpretator.common.errors;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 09.02.2005
 * Time: 23:55:10
 */
public class UserErrorException extends RuntimeException {

    private int line;

    public int getLine() {
        return line;
    }

    public UserErrorException(String message, int _line) {
        super(message);
        line = _line;
    }
}
