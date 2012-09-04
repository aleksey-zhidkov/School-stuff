package ru.lexx.acsystem.interpretator.common.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 30.10.2005
 * Time: 14:13:46
 */
public class ElseLexem extends AbstractKeyWordLexem {

    private boolean isSkipping;

    public ElseLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
        int len = context.getBlockLength();
        if (isSkipping)
            context.getLexemByShift(1).setMovePointerShift(len);
    }

    public boolean isSkipping() {
        return isSkipping;
    }

    public void setIsSkipping(boolean _isSkipping) {
        isSkipping = _isSkipping;
    }
}
