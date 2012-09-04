package ru.lexx.acsystem.interpretator.pascal.function;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.constant.ConstantManager;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.function.IFunction;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 02.10.2005
 * Time: 0:36:30
 * To change this template use File | Settings | File Templates.
 */
public class StrFunction implements IFunction {
    public IConstant execute(ProgramContext context) {
        int i = 0;
        DataType exprType = DataType.TYPE_INTEGER;
        List<ILexem> lst = new ArrayList<ILexem>();
        for (; ; i++) {
            ILexem lexem = context.getNextLexem();
            if (",".equalsIgnoreCase(lexem.getValue()))
                break;
            Lexem type = lexem.getLexemType();
            if (type == Lexem.LEXEM_CONSTANT || type == Lexem.LEXEM_OPERATOR) {
                lst.add(lexem);
                if (lexem.getValue().indexOf('.') != -1)
                    exprType = DataType.TYPE_FLOAT;
            } else if (type == Lexem.LEXEM_VARIABLE) {
                lst.add(ConstantManager.createConstant(context.getVariable(lexem.getValue()).getVarValue().toString(), context.getVariable(lexem.getValue()).getType(), lexem.getLine()));
                if (context.getVariable(lexem.getValue()).getType() == DataType.TYPE_FLOAT)
                    exprType = DataType.TYPE_FLOAT;
            } else if (type == Lexem.LEXEM_FUNCTION) {
                String res = context.getParser().parseFunction(context, lexem);
                lst.add(ConstantManager.createConstant(res, context.getFuncManager().getFunctionType(lexem.getValue()), lexem.getLine()));
                if (res.indexOf('.') != -1)
                    exprType = DataType.TYPE_FLOAT;
            }
        }
        ILexem[] lexems = new ILexem[lst.size()];
        if (exprType == DataType.TYPE_FLOAT)
            context.putValueToVariable(context.getNextLexem().getValue(), context.getCalculator().calculateDouble(lst.toArray(lexems)) + "");
        else
            context.putValueToVariable(context.getNextLexem().getValue(), context.getCalculator().calculateInt(lst.toArray(lexems)) + "");
        return IConstant.VOID_CONSTANT;
    }

    public DataType getReturnType() {
        return DataType.TYPE_VOID;
    }
}
