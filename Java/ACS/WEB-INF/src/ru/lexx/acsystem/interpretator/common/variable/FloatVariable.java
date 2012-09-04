package ru.lexx.acsystem.interpretator.common.variable;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractLexem;

public class FloatVariable extends AbstractLexem implements NumericVariable {

    double varValue;

    public FloatVariable(String _name, int _line) {
        this(0d, _name, _line);
    }

    public FloatVariable(double _value, String _name, int _line) {
        super(_name, _line);
        varValue = _value;
    }

    public DataType getType() {
        return DataType.TYPE_FLOAT;
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
        FloatVariable v = (FloatVariable) _v;
        double accuracy = ((Double) _accuracy).doubleValue();
        return varValue + accuracy > v.getFloatValue() && varValue - accuracy < v.getFloatValue();
    }

    public Object getVarValue() {
        return new Double(varValue);
    }

    public void setVarValue(Object _value) {
        if (!(_value instanceof Double))
            throw new UserErrorException("Несоответсвие типов: ожидается 'real'", line);
        varValue = ((Double) _value).doubleValue();
    }

    public double getFloatValue() {
        return varValue;
    }

    public FloatVariable clone() {
        return new FloatVariable(varValue, value, line);
    }
}
