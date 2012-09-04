package ru.lexx.acsystem.interpretator.common.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 13:34:03
 * To change this template use File | Settings | File Templates.
 */
public class IfLexem extends AbstractKeyWordLexem {
    public IfLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
        boolean res = context.getParser().parseBooleanExpression(context);
        context.movePointer(-1);
        int len = context.getBlockLength();
        if (!"then".equalsIgnoreCase(context.getCurLexem().getValue()))
            throw new UserErrorException("Ожидается 'then'", getLine());
        if (res) {
            ILexem else_lexem = context.getLexemByShift(len + 1);
            if (else_lexem instanceof ElseLexem)
                ((ElseLexem) else_lexem).setIsSkipping(true);
        } else {
            ILexem else_lexem = context.getLexemByShift(len + 1);
            if (else_lexem instanceof ElseLexem)
                ((ElseLexem) else_lexem).setIsSkipping(false);
            ILexem l = context.getLexemByShift(1);
            l.setMovePointerShift(len);
        }
    }
}
