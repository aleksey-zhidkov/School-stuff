package ru.lexx.acsystem.interpretator.pascal.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractKeyWordLexem;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 14:12:13
 * To change this template use File | Settings | File Templates.
 */
public class ThenLexem extends AbstractKeyWordLexem {
    public ThenLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
    }
}
