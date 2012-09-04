package ru.lexx.acsystem.interpretator.common.constant;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.errors.IncompatibleTypesException;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 09.02.2005
 * Time: 23:12:34
 */
public class BooleanConstant extends AbstractConstantLexem implements IConstant {

    BooleanConstant(String _value, int _line) {
        super(_value, _line);
        if (!("true".equalsIgnoreCase(_value) || "false".equalsIgnoreCase(_value)))
            throw new IncompatibleTypesException("Неподходящее значение: '" + _value + "' ожидается true или false", 0);
    }

    public DataType getType() {
        return DataType.TYPE_BOOLEAN;
    }
}
