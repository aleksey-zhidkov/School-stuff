package ru.lexx.acsystem.interpretator.common.constant;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.errors.IncompatibleTypesException;


/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 02.10.2005
 * Time: 0:02:47
 */
public class IntegerConstant extends AbstractConstantLexem implements IConstant {

    IntegerConstant(String _value, int _line) {
        super(_value, _line);
        try {
            Integer.parseInt(_value);
        }
        catch (NumberFormatException e) {
            throw new IncompatibleTypesException("Неподходящее значение: " + _value + " ожидается целое число", 0);
        }
    }

    public DataType getType() {
        return DataType.TYPE_INTEGER;
    }
}
