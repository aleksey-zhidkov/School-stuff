package ru.lexx.acsystem.interpretator.pascal.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractKeyWordLexem;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 07.11.2005
 * Time: 13:57:38
 */
public class OfLexem extends AbstractKeyWordLexem {
    public OfLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
    }
}
