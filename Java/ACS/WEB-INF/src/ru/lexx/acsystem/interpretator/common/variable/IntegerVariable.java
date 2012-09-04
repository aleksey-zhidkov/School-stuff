package ru.lexx.acsystem.interpretator.common.variable;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractLexem;

public class IntegerVariable extends AbstractLexem implements NumericVariable {

    int varValue;

    public IntegerVariable(String _name, int _line) {
        this(0, _name, _line);
    }

    public IntegerVariable(int _value, String _name, int _line) {
        super(_name, _line);
        varValue = _value;
    }

    public DataType getType() {
        return DataType.TYPE_INTEGER;
    }

    public Lexem getLexemType() {
        return Lexem.LEXEM_VARIABLE;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String _value) {
        value = _value;
    }

    public String getName() {
        return value;
    }

    public void setName(String _name) {
        value = _name;
    }

    public boolean equalsTo(IVariable _v, Object _accuracy) {
        return varValue == ((Integer) _v.getVarValue()).intValue();
    }

    public Object getVarValue() {
        return varValue;
    }

    public void setVarValue(Object _value) {
        if (!(_value instanceof Integer))
            throw new UserErrorException("Несоответсвие типов: ожидается 'integer'", line);
        varValue = ((Integer) _value).intValue();
    }

    public int getIntValue() {
        return varValue;
    }

    public IntegerVariable clone() {
        return new IntegerVariable(value, varValue);
    }

    public String toString() {
        return "Integer: [ " + value + " , " + varValue + " ]";
    }
}
