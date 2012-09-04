package ru.lexx.acsystem.interpretator.pascal.lexem;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractKeyWordLexem;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 15:43:52
 */
public class ForLexem extends AbstractKeyWordLexem {

    private boolean isInit = true;
    private int to;
    private IVariable counter;
    private int shift = 1;

    public ForLexem(String value, int _line) {
        super(value, _line);
    }

    public void changeState(ProgramContext context) {
        int myPos = context.getLexemPointer();
        if (isInit) {
            for (; !":=".equalsIgnoreCase(context.getCurLexem().getValue()); context.incrementPointer())
                ;
            counter = context.getVariableFromLexem(context.getLexemPointer() - 1);
            context.getParser().doAssigment(context);
            context.movePointer(-1);
            for (; !(context.getCurLexem().getLexemType() == Lexem.LEXEM_KEYWORD); context.incrementPointer())
                ;
            if ("downto".equalsIgnoreCase(context.getCurLexem().getValue()))
                shift = -1;
            else if ("to".equalsIgnoreCase(context.getCurLexem().getValue()))
                shift = 1;
            else
                throw new UserErrorException("ќжидаетс€ 'to' или 'downto'", context.getCurLexem().getLine());
            to = Integer.parseInt(context.getParser().parseNumericExpression(context, DataType.TYPE_INTEGER).getValue());
            isInit = false;
            context.movePointer(-1);
        } else {
            context.putValueToVariable(counter, Integer.parseInt(counter.getVarValue().toString()) + shift + "");
        }
        for (; !"do".equalsIgnoreCase(context.getCurLexem().getValue()); context.incrementPointer())
            ;
        int len = context.getBlockLength();
        if (shift == 1 && ((Integer) counter.getVarValue()).intValue() <= to ||
            shift == -1 && ((Integer) counter.getVarValue()).intValue() >= to)
            context.getLexemByShift(len + 1).setMovePointerShift(-(context.getLexemPointer() + len - myPos + 1));
        else {
            context.putValueToVariable(counter, Integer.parseInt(counter.getVarValue().toString()) + -shift + "");
            context.getLexemByShift(len + 1).setMovePointerShift(0);
            context.movePointer(len);
        }
    }
}
