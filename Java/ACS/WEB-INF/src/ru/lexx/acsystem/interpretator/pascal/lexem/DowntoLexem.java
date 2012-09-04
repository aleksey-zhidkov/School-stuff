package ru.lexx.acsystem.interpretator.pascal.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractKeyWordLexem;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 05.11.2005
 * Time: 1:41:51
 */
public class DowntoLexem extends AbstractKeyWordLexem {
    protected DowntoLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
    }
}
