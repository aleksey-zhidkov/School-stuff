package ru.lexx.acsystem.backend.constants;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 30.09.2005
 * Time: 21:53:44
 * To change this template use File | Settings | File Templates.
 */
public class ProgLanguage extends AbstractConstant {

    public static final ProgLanguage PASCAL = new ProgLanguage("Pascal", "1");
    public static final ProgLanguage NONE = new ProgLanguage("None", "0");

    private ProgLanguage(String _name, String _code) {
        super(_name, _code);
    }

    public static ProgLanguage getByName(String _name) {
        return (ProgLanguage) getConstantByName(_name, ProgLanguage.class);
    }

    public static ProgLanguage getByCode(String _code) {
        return (ProgLanguage) getConstantByCode(_code, ProgLanguage.class);
    }

    public static ProgLanguage[] getAll() {
        AbstractConstant[] ac = getAll(ProgLanguage.class);
        ProgLanguage[] res = new ProgLanguage[ac.length];
        for (int i = 0; i < ac.length; i++)
            res[i] = (ProgLanguage) ac[i];
        return res;
    }
}
