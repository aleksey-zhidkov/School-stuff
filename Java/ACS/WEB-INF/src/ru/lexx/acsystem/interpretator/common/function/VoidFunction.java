package ru.lexx.acsystem.interpretator.common.function;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 04.10.2005
 * Time: 23:15:00
 */
public class VoidFunction implements IFunction {
    public IConstant execute(ProgramContext context) {
        return IConstant.VOID_CONSTANT;
    }

    public DataType getReturnType() {
        return DataType.TYPE_VOID;
    }
}
