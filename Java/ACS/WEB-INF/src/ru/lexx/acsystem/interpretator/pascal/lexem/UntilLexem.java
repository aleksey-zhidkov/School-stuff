package ru.lexx.acsystem.interpretator.pascal.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractKeyWordLexem;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 14:04:47
 */
public class UntilLexem extends AbstractKeyWordLexem {

    public UntilLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
        boolean res = context.getParser().parseBooleanExpression(context);
        if (!res) {
            for (; !"repeat".equalsIgnoreCase(context.getCurLexem().getValue()) && context.getLexemPointer() > 0; context.movePointer(-1))
                ;
            if (context.getLexemPointer() == 0)
                throw new UserErrorException("Ожидается 'repeat'", getLine());
        }
    }
}
