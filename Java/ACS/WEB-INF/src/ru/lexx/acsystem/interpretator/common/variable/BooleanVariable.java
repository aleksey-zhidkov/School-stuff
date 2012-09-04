package ru.lexx.acsystem.interpretator.common.variable;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractLexem;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 08.03.2006
 * Time: 19:33:08
 */
public class BooleanVariable extends AbstractLexem implements IVariable {

    private String name;
    private boolean varValue;

    public BooleanVariable(String _name, int _line) {
        this(false, _name, _line);
    }

    public BooleanVariable(boolean _value, String _name, int _line) {
        super(_name, _line);
        name = _name;
        varValue = _value;
    }

    public DataType getType() {
        return DataType.TYPE_BOOLEAN;
    }

    public String getName() {
        return name;
    }

    public boolean equalsTo(IVariable variable, Object obj) {
        if (variable.getType() == DataType.TYPE_BOOLEAN)
            return varValue == ((Boolean) variable.getVarValue()).booleanValue();
        else
            return false;
    }

    public Object getVarValue() {
        return Boolean.valueOf(varValue);
    }

    public void setVarValue(Object _value) {
        if (!(_value instanceof Boolean))
            throw new UserErrorException("Несоответсвие типов: ожидается 'boolean'", line);
        varValue = ((Boolean) _value).booleanValue();
    }

    public Lexem getLexemType() {
        return Lexem.LEXEM_VARIABLE;
    }

    public String getValue() {
        return name;
    }

    public void setValue(Object obj) {
        if (!(obj instanceof Boolean))
            throw new UserErrorException("Несоответсвие типов: ожидается 'boolean'", 0);
        varValue = ((Boolean) obj).booleanValue();
    }

    public Object clone() {
        return Boolean.valueOf(varValue);
    }
}
