package ru.lexx.acsystem.interpretator.common.lexem;

import ru.lexx.acsystem.backend.constants.Lexem;

public interface ILexem {

    Lexem getLexemType();

    String getValue();

    int getMovePointerShift();

    void setMovePointerShift(int shift);

    int getLine();

    void setLine(int line);

}
