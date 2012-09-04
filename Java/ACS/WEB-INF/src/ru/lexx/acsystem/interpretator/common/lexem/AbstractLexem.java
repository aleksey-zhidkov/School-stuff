package ru.lexx.acsystem.interpretator.common.lexem;


public abstract class AbstractLexem implements ILexem {

    protected String value;

    protected int line;

    private int movePointerShift;

    public void setLine(int _line) {
        line = _line;
    }

    protected AbstractLexem(String _value, int _line) {
        value = _value;
        line = _line;
    }

    public String getValue() {
        return value;
    }

    public int getMovePointerShift() {
        return movePointerShift;
    }

    public void setMovePointerShift(int shift) {
        movePointerShift = shift;
    }

    public int getLine() {
        return line;
    }
}
