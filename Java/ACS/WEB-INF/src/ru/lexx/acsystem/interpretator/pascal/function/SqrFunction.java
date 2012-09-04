package ru.lexx.acsystem.interpretator.pascal.function;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.constant.ConstantManager;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.function.IFunction;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 13.10.2005
 * Time: 23:53:59
 * To change this template use File | Settings | File Templates.
 */
public class SqrFunction implements IFunction {
    public IConstant execute(ProgramContext context) {
        int line = context.getCurLine();
        double res = Double.parseDouble(context.getParser().parseParameters(context, DataType.TYPE_FLOAT).getValue());
        res *= res;
        return ConstantManager.createConstant(res, line);
    }

    public DataType getReturnType() {
        return DataType.TYPE_FLOAT;
    }
}
