package ru.lexx.acsystem.interpretator.common.variable;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;

public interface IVariable extends Cloneable, ILexem {

    DataType getType();

    String getName();

    boolean equalsTo(IVariable variable, Object obj);

    Object getVarValue();

    void setVarValue(Object obj);

    Object clone();
}
