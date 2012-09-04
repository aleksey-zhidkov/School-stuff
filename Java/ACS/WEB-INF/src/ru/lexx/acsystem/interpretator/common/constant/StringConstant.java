package ru.lexx.acsystem.interpretator.common.constant;

import ru.lexx.acsystem.backend.constants.DataType;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 02.10.2005
 * Time: 0:55:40
 * To change this template use File | Settings | File Templates.
 */
public class StringConstant extends AbstractConstantLexem implements IConstant {

    StringConstant(String _value, int _line) {
        super(_value, _line);
    }

    public DataType getType() {
        return DataType.TYPE_STRING;
    }
}
