package ru.lexx.acsystem.backend.constants;

import ru.lexx.acsystem.backend.system.SystemManager;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 20.08.2005
 * Time: 23:56:12
 */
public class AbstractConstant {
    private String name;
    private String code;

    protected AbstractConstant(String _name, String _code) {
        name = _name;
        code = _code;
    }

    protected static AbstractConstant getConstantByName(String cname, Class cl) {
        Field[] fields = cl.getFields();
        for (Field fl : fields) {
            int mod = fl.getModifiers();
            if (mod == (Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL)) {
                try {
                    AbstractConstant o = (AbstractConstant) fl.get(null);
                    if (o.name.equalsIgnoreCase(cname))
                        return o;
                }
                catch (IllegalAccessException e) {
                    SystemManager.getLogger().log(AbstractConstant.class.getName() + ".getConstantByName(String name, Class cl)", e);
                }
            }
        }
        return null;
    }

    protected static AbstractConstant getConstantByCode(String _code, Class cl) {
        Field[] fields = cl.getFields();
        for (Field fl : fields) {
            int mod = fl.getModifiers();
            if (mod == (Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL)) {
                try {
                    AbstractConstant o = (AbstractConstant) fl.get(null);
                    if (o.code.equalsIgnoreCase(_code))
                        return o;
                }
                catch (IllegalAccessException e) {
                    SystemManager.getLogger().log(AbstractConstant.class.getName() + ".getConstantByName(String name, Class cl)", e);
                }
            }
        }
        return null;
    }

    protected static AbstractConstant[] getAll(Class cl) {
        Field[] fields = cl.getDeclaredFields();
        AbstractConstant[] res = new AbstractConstant[fields.length];
        int j = 0;
        for (int i = 0; i < res.length; i++) {
            if (fields[i].getModifiers() == Modifier.PUBLIC + Modifier.STATIC + Modifier.FINAL) {
                try {
                    AbstractConstant o = (AbstractConstant) fields[i].get(null);
                    res[j++] = o;
                }
                catch (IllegalAccessException e) {
                    SystemManager.getLogger().log(AbstractConstant.class.getName() + ".getConstantByName(String name, Class cl)", e);
                }
                catch (ClassCastException e) {
                    SystemManager.getLogger().log(AbstractConstant.class.getName() + ".getConstantByName()", e);
                }
            }
        }
        AbstractConstant[] res2 = new AbstractConstant[j];
        System.arraycopy(res, 0, res2, 0, j);
        return res2;
    }

    public String toString() {
        return "{ " + name + "," + code + " }";
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public boolean equals(Object other) {
        if (!(other instanceof AbstractConstant))
            return false;
        AbstractConstant o = (AbstractConstant) other;
        return o.getCode().equals(code) && o.getName().equals(name);
    }
}
