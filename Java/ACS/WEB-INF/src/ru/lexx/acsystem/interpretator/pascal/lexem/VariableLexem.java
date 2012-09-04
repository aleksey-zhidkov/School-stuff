package ru.lexx.acsystem.interpretator.pascal.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractKeyWordLexem;
import ru.lexx.acsystem.interpretator.common.lexem.UsersDefinedLexem;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 14.10.2005
 * Time: 0:32:49
 */
public class VariableLexem extends AbstractKeyWordLexem {
    public VariableLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
        String type = context.getCurLexem().getValue().toLowerCase();
        for (int i = context.getLexemPointer() - 1; context.getLexem(i) instanceof UsersDefinedLexem || ",".equals(context.getLexem(i).getValue()); i--)
            if (!",".equals(context.getLexem(i).getValue()))
                context.createVariable(type, context.getLexem(i).getValue());
    }
}
