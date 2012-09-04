package ru.lexx.acsystem.backend.constants;

public class DataType extends AbstractConstant {

    public static final DataType TYPE_FLOAT = new DataType("TYPE_FLOAT", "real");
    public static final DataType TYPE_INTEGER = new DataType("TYPE_INTEGER", "integer");
    public static final DataType TYPE_STRING = new DataType("TYPE_STRING", "string");
    public static final DataType TYPE_BOOLEAN = new DataType("TYPE_BOOLEAN", "boolean");
    public static final DataType TYPE_VOID = new DataType("TYPE_VOID", "void");

    private DataType(String _name, String _code) {
        super(_name, _code);
    }

    public static DataType getByName(String _name) {
        return (DataType) getConstantByName(_name, DataType.class);
    }

    public static DataType getByCode(String _code) {
        return (DataType) getConstantByName(_code, DataType.class);
    }

    public static DataType[] getAll() {
        AbstractConstant ac[] = getAll(DataType.class);
        DataType res[] = new DataType[ac.length];
        for (int i = 0; i < ac.length; i++)
            res[i] = (DataType) ac[i];

        return res;
    }

    public static boolean compatible(DataType t1, DataType t2) {
        if (t1 == TYPE_INTEGER && (t2 == TYPE_INTEGER || t2 == TYPE_FLOAT))
            return true;
        else if (t1 == TYPE_FLOAT && (t2 == TYPE_INTEGER || t2 == TYPE_FLOAT))
            return true;
        else if (t1 == TYPE_STRING && t2 == TYPE_STRING)
            return true;
        else if (t1 == TYPE_BOOLEAN && t2 == TYPE_BOOLEAN)
            return true;
        return false;
    }
}
