package ru.lexx.acsystem.backend.constants;

public class Lexem extends AbstractConstant {

    public static final Lexem LEXEM_KEYWORD = new Lexem("LEXEM_KEYWORD", "keyword");
    public static final Lexem LEXEM_VARIABLE = new Lexem("LEXEM_VARIABLE", "variable");
    public static final Lexem LEXEM_OPERATOR = new Lexem("LEXEM_OPERATOR", "operator");
    public static final Lexem LEXEM_FUNCTION = new Lexem("LEXEM_FUNCTION", "function");
    public static final Lexem LEXEM_CONSTANT = new Lexem("LEXEM_CONSTANT", "constant");

    private Lexem(String _name, String _code) {
        super(_name, _code);
    }

    public static Lexem getByName(String _name) {
        return (Lexem) getConstantByName(_name, Lexem.class);
    }

    public static Lexem getByCode(String _code) {
        return (Lexem) getConstantByName(_code, Lexem.class);
    }

    public static Lexem[] getAll() {
        AbstractConstant[] ac = getAll(Lexem.class);
        Lexem[] res = new Lexem[ac.length];
        for (int i = 0; i < ac.length; i++)
            res[i] = (Lexem) ac[i];
        return res;
    }
}
