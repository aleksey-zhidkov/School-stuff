package ru.lexx.acsystem.interpretator.common;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 11.02.2005
 * Time: 10:32:28
 */
public interface IParser {

    ILexem[] parse(String program) throws Exception;

    void doAssigment(ProgramContext context);

    IConstant parseNumericExpression(ProgramContext context, DataType type);

    String parseFunction(ProgramContext context, ILexem lexem);

    void parseStringExpression(ProgramContext context);

    IConstant parseParameters(ProgramContext context, DataType dtype);

    boolean parseBooleanExpression(ProgramContext context);

}
