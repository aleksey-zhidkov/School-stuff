package ru.lexx.acsystem.interpretator.pascal.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractKeyWordLexem;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 15:45:37
 */
public class ToLexem extends AbstractKeyWordLexem {

    public ToLexem(String value, int _line) {
        super(value, _line);
    }

    public void changeState(ProgramContext context) {
    }
}
