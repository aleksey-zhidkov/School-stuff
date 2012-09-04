package ru.lexx.acsystem.interpretator.common.lexem;

import ru.lexx.acsystem.backend.constants.Lexem;

public abstract class AbstractKeyWordLexem extends AbstractLexem
        implements IKeyWordLexem {

    protected AbstractKeyWordLexem(String _value, int _line) {
        super(_value, _line);
    }

    public Lexem getLexemType() {
        return Lexem.LEXEM_KEYWORD;
    }
}
