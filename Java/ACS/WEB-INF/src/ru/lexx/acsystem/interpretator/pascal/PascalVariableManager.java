package ru.lexx.acsystem.interpretator.pascal;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.variable.*;

public class PascalVariableManager
        implements IVariableManager {

    public PascalVariableManager() {
    }

    public IVariable createVariable(DataType type, String name, String value) {
        if (type == DataType.TYPE_INTEGER) {
            String val;
            int pos = value.indexOf(".");
            if (pos != -1)
                val = value.substring(0, pos);
            else
                val = value;
            return new IntegerVariable(Integer.parseInt(val), name, 0);
        } else if (type == DataType.TYPE_FLOAT) {
            return new FloatVariable(Double.parseDouble(value), name, 0);
        } else if (type == DataType.TYPE_STRING) {
            return new StringVariable(value, name, 0);
        } else if (type == DataType.TYPE_BOOLEAN) {
            return new BooleanVariable(Boolean.parseBoolean(value), name, 0);
        }

        throw new IllegalArgumentException("DataType " + type.getName() + " aren't implemented yet");
    }

    public void putValueToVariable(IVariable var, String val) {
        DataType varType = var.getType();
        if (varType == DataType.TYPE_FLOAT) {
            try {
                var.setVarValue(new Double(val));
            }
            catch (NumberFormatException e) {
                throw new UserErrorException("Не подходящее значение: " + val + ", когда ожидается вещественное число", 0);
            }
        } else if (varType == DataType.TYPE_INTEGER) {
            try {
                if (val.indexOf('.') != -1)
                    var.setVarValue(new Integer(val.substring(0, val.indexOf('.'))));
                else
                    var.setVarValue(new Integer(val));
            }
            catch (NumberFormatException e) {
                throw new UserErrorException("Не подходящее значение: " + val + ", когда ожидается целое число", 0);
            }
        } else if (varType == DataType.TYPE_STRING)
            var.setVarValue(val);
        else if (varType == DataType.TYPE_BOOLEAN) {
            if (!("true".equalsIgnoreCase(val) || "false".equalsIgnoreCase(val)))
                throw new UserErrorException("Не подходящее значение: " + val + ", когда ожидается 'true' или 'false'", 0);
            var.setVarValue(new Double(val));
        }
    }

    public IVariable createVariable(DataType type, String name) {
        if (type == DataType.TYPE_INTEGER) {
            return new IntegerVariable(name, 0);
        } else if (type == DataType.TYPE_FLOAT) {
            return new FloatVariable(name, 0);
        } else if (type == DataType.TYPE_STRING) {
            return new StringVariable(name, 0);
        } else if (type == DataType.TYPE_BOOLEAN) {
            return new BooleanVariable(name, 0);
        }

        throw new IllegalArgumentException("DataType " + type.getName() + " aren't implemented yet");
    }

    public ArrayVariable createArray(DataType type, int bi, int ei, String name) {
        return new ArrayVariable(bi, ei, name, 0);
    }

    public DataType getInternalType(String langType) {
        if ("integer".equalsIgnoreCase(langType) || "int".equalsIgnoreCase(langType))
            return DataType.TYPE_INTEGER;
        else if ("real".equalsIgnoreCase(langType) || "float".equalsIgnoreCase(langType) ||
                 "double".equalsIgnoreCase(langType))
            return DataType.TYPE_FLOAT;
        else if ("string".equalsIgnoreCase(langType))
            return DataType.TYPE_STRING;
        else if ("boolean".equalsIgnoreCase(langType))
            return DataType.TYPE_BOOLEAN;
        throw new IllegalArgumentException("Unknown DataType: " + langType);
    }

    public IVariable cast(IVariable var, DataType type) {
        if (type == DataType.TYPE_INTEGER)
            return castToInteger(var);
        else if (type == DataType.TYPE_FLOAT)
            return castToFloat(var);
        else if (type == DataType.TYPE_BOOLEAN)
            return castToBoolean(var);
        return null;
    }

    public IntegerVariable castToInteger(IVariable var) {
        if (var.getType() == DataType.TYPE_INTEGER)
            return (IntegerVariable) var;
        else if (var.getType() == DataType.TYPE_FLOAT)
            return (IntegerVariable) createVariable(DataType.TYPE_INTEGER, var.getName(), var.getVarValue().toString());
        else
            throw new UserErrorException("Не соответствие типов: ожидается число, полученно " + var.getType().getCode(), 0);
    }

    public FloatVariable castToFloat(IVariable var) {
        if (var.getType() == DataType.TYPE_FLOAT)
            return (FloatVariable) var;
        else if (var.getType() == DataType.TYPE_INTEGER)
            return (FloatVariable) createVariable(DataType.TYPE_FLOAT, var.getName(), var.getVarValue().toString());
        throw new UserErrorException("Не соответствие типов: ожидается число, полученно " + var.getType().getCode(), 0);
    }

    public BooleanVariable castToBoolean(IVariable var) {
        return (BooleanVariable) createVariable(DataType.TYPE_BOOLEAN, var.getName(), var.getVarValue().toString());
    }
}
