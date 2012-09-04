package ru.lexx.acsystem.interpretator.common.constant;

import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractLexem;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 09.02.2005
 * Time: 23:19:07
 */
public class AbstractConstantLexem extends AbstractLexem {

    protected AbstractConstantLexem(String _value, int _line) {
        super(_value, _line);
    }

    public Lexem getLexemType() {
        return Lexem.LEXEM_CONSTANT;
    }
}
