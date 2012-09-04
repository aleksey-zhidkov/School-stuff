package ru.lexx.acsystem.interpretator.pascal.function;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.function.IFunction;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 15:20:58
 */
public class IncFunction implements IFunction {
    public IConstant execute(ProgramContext context) {
        context.getNextLexem();
        IVariable v = context.getVariableFromLexem(context.getLexemPointer());
        context.putValueToVariable(v, Integer.toString(((Integer) v.getVarValue()).intValue() + 1));
        return IConstant.VOID_CONSTANT;
    }

    public DataType getReturnType() {
        return DataType.TYPE_VOID;
    }
}
