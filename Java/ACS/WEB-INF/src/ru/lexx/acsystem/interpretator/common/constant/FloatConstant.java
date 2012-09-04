package ru.lexx.acsystem.interpretator.common.constant;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.errors.IncompatibleTypesException;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 02.10.2005
 * Time: 0:56:45
 * To change this template use File | Settings | File Templates.
 */
public class FloatConstant extends AbstractConstantLexem implements IConstant {

    FloatConstant(String _value, int _line) {
        super(_value, _line);
        try {
            Float.parseFloat(_value);
        } catch (NumberFormatException e) {
            throw new IncompatibleTypesException("Неподходящее значение: " + _value + " ожидается вещественное число", 0);
        }
    }

    public DataType getType() {
        return DataType.TYPE_FLOAT;
    }
}
