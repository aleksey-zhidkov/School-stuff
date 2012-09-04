package ru.lexx.acsystem.interpretator.common.lexem;

import ru.lexx.acsystem.backend.constants.Lexem;

public class FunctionLexem extends AbstractLexem {

    public FunctionLexem(String _value, int _line) {
        super(_value, _line);
    }

    public Lexem getLexemType() {
        return Lexem.LEXEM_FUNCTION;
    }
}
