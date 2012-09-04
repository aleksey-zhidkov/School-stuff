package ru.lexx.acsystem.interpretator.common.variable;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractLexem;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 02.10.2005
 * Time: 0:45:34
 */
public class StringVariable extends AbstractLexem implements IVariable {
    private String varValue;

    public StringVariable(String _name, int _line) {
        this("", _name, _line);
    }

    public StringVariable(String _value, String _name, int _line) {
        super(_name, _line);
        varValue = _value;
    }

    public DataType getType() {
        return DataType.TYPE_STRING;
    }

    public Lexem getLexemType() {
        return Lexem.LEXEM_VARIABLE;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String obj) {
        value = obj;
    }

    public String getName() {
        return value;
    }

    public boolean equalsTo(IVariable variable, Object obj) {
        if (!(variable instanceof StringVariable))
            return false;
        if (obj instanceof Boolean)
            if (((Boolean) obj).booleanValue())
                return varValue.equalsIgnoreCase((String) variable.getVarValue());
            else
                return varValue.equals((String) variable.getVarValue());
        else
            return varValue.equals((String) variable.getVarValue());
    }

    public Object getVarValue() {
        return varValue;
    }

    public void setVarValue(Object _value) {
        if (!(_value instanceof String))
            throw new UserErrorException("Несоответсвие типов: ожидается 'string'", line);
        varValue = (String) _value;
    }

    public StringVariable clone() {
        return new StringVariable(varValue, value, line);
    }
}
