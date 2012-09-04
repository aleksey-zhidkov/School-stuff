package ru.lexx.acsystem.interpretator.pascal.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractKeyWordLexem;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 14:04:02
 */
public class RepeatLexem extends AbstractKeyWordLexem {

    public RepeatLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {

    }
}
