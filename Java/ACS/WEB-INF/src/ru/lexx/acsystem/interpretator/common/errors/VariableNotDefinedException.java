package ru.lexx.acsystem.interpretator.common.errors;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 25.06.2006
 * Time: 15:51:46
 */
public class VariableNotDefinedException extends UserErrorException{

    public VariableNotDefinedException(String message, int _line) {
        super(message, _line);
    }
    
}
