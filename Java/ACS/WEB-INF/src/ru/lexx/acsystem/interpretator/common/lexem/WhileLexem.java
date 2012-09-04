package ru.lexx.acsystem.interpretator.common.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 12:42:35
 */
public class WhileLexem extends AbstractKeyWordLexem {
    public WhileLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
        int myPos = context.getLexemPointer();
        boolean res = context.getParser().parseBooleanExpression(context);
        int len = context.getBlockLength();
        if (!"do".equalsIgnoreCase(context.getCurLexem().getValue()))
            throw new UserErrorException("Ожидается 'do'", getLine());
        if (res) {
            context.getLexemByShift(len + 1).setMovePointerShift(-(context.getLexemPointer() + len - myPos + 1));
        } else {
            context.getLexemByShift(len + 1).setMovePointerShift(0);
            context.movePointer(len);
        }
    }
}
