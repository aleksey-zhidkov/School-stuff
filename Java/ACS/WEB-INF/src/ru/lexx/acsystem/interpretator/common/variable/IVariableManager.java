package ru.lexx.acsystem.interpretator.common.variable;

import ru.lexx.acsystem.backend.constants.DataType;

public interface IVariableManager {

    IVariable createVariable(DataType type, String name, String value);

    IVariable createVariable(DataType type, String name);

    void putValueToVariable(IVariable variable, String val);

    ArrayVariable createArray(DataType type, int bi, int ei, String name);

    DataType getInternalType(String langType);

    IVariable cast(IVariable var, DataType type);
}
