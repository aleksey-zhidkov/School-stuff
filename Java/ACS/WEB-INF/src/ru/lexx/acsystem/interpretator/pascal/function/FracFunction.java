package ru.lexx.acsystem.interpretator.pascal.function;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.constant.ConstantManager;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.function.IFunction;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 02.10.2005
 * Time: 0:31:00
 * To change this template use File | Settings | File Templates.
 */
public class FracFunction implements IFunction {
    public IConstant execute(ProgramContext context) {
        int line = context.getCurLexem().getLine();
        String param = context.getParser().parseParameters(context, DataType.TYPE_FLOAT).getValue();
        param = "0." + param.substring(param.indexOf('.') + 1);
        return ConstantManager.createConstant(Double.parseDouble(param), line);
    }

    public DataType getReturnType() {
        return DataType.TYPE_FLOAT;
    }
}
