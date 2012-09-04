package ru.lexx.acsystem.interpretator.common.variable;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractLexem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 07.11.2005
 * Time: 14:30:28
 */
public class ArrayVariable extends AbstractLexem implements IVariable, Cloneable {
    private int beginIndex;
    private int endIndex;
    private List<IVariable> vals;
    private String name;

    public ArrayVariable(int _beginIndex, int _endIndex, String _name, int _line) {
        super(_name, _line);
        beginIndex = _beginIndex;
        endIndex = _endIndex;
        vals = new ArrayList<IVariable>(endIndex - beginIndex + 1);
        name = _name;
    }

    public DataType getType() {
        return vals.get(beginIndex).getType();
    }

    public String getName() {
        return name;
    }

    public boolean equalsTo(IVariable variable, Object obj) {
        if (!(variable instanceof ArrayVariable))
            return false;
        ArrayVariable v2 = (ArrayVariable) variable;
        if (beginIndex != v2.beginIndex)
            return false;
        if (endIndex != v2.endIndex)
            return false;
        boolean res = true;
        for (int i = beginIndex; i <= endIndex; i++)
            res &= vals.get(i).equalsTo(v2.get(i), null);
        return res;
    }

    public Object getVarValue() {
        return vals;
    }

    public void setVarValue(Object obj) {
        vals = (List<IVariable>) obj;
    }

    public Lexem getLexemType() {
        return Lexem.LEXEM_VARIABLE;
    }

    public String getValue() {
        return name;
    }

    public void setValue(Object obj) {
        vals = (List<IVariable>) obj;
    }

    public IVariable get(int i) {
        //todo throw PascalArrayIndexOutOfBoundsException
        return vals.get(i - beginIndex);
    }

    public void set(int i, IVariable v) {
        //todo throw PascalArrayIndexOutOfBoundsException
        //todo throw PascalTypeCastException
        vals.add(i - beginIndex, v);
    }

    public ArrayVariable clone() {
        ArrayVariable newvar = new ArrayVariable(beginIndex, endIndex, name, line);
        for (int i = beginIndex; i <= endIndex; i++)
            newvar.set(i, (IVariable) get(i).clone());
        return newvar;
    }

    public String toString() {
        StringBuffer str = new StringBuffer("Array [" + name + ", " + beginIndex + ", " + endIndex + "] :\n");
        for (int i = beginIndex; i < endIndex; i++) {
            str.append("  ");
            str.append(get(i).toString());
        }
        str.append("\n");
        return str.toString();
    }

    public int size() {
        return vals.size();
    }
}
