package ru.lexx.acsystem.interpretator.common.function;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 10.02.2005
 * Time: 23:22:06
 */
public interface IFunctionManager {

    boolean isFunction(String name);

    IConstant executeFunction(ProgramContext context, ILexem function);

    DataType getFunctionType(String name);

}
