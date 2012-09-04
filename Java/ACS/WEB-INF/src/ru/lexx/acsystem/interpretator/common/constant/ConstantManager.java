package ru.lexx.acsystem.interpretator.common.constant;

import ru.lexx.acsystem.backend.constants.DataType;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 16:52:11
 */
public class ConstantManager {
    private ConstantManager() {
    }

    public static IConstant createConstant(String value, DataType type, int line) {
        if (type == DataType.TYPE_INTEGER) {
            String val;
            int pos = value.indexOf(".");
            if (pos != -1)
                val = value.substring(0, pos);
            else
                val = value;
            return new IntegerConstant(val, line);
        } else if (type == DataType.TYPE_FLOAT) {
            return new FloatConstant(value, line);
        } else if (type == DataType.TYPE_STRING) {
            return new StringConstant(value, line);
        } else if (type == DataType.TYPE_BOOLEAN) {
            return new BooleanConstant(value, line);
        }

        throw new IllegalArgumentException("DataType " + type.getName() + " aren't implemented yet");
    }

    public static IConstant createConstant(String value, int line) {
        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return new BooleanConstant(value, line);
        } else if (value.matches("\\p{Alpha}.")) {
            return new StringConstant(value, line);
        } else if (value.indexOf('.') != -1) {
            return new FloatConstant(value, line);
        } else {
            return new IntegerConstant(value, line);
        }
    }

    public static IConstant createConstant(double value, int line) {
        return new FloatConstant(value + "", line);
    }

    public static IConstant createConstant(int value, int line) {
        return new IntegerConstant(value + "", line);
    }
}
