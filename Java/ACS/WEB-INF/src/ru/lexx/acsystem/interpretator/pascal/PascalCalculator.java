package ru.lexx.acsystem.interpretator.pascal;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.interpretator.common.CommonCalculator;
import ru.lexx.acsystem.interpretator.common.constant.ConstantManager;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 16:26:13
 */
public class PascalCalculator extends CommonCalculator {

    public PascalCalculator(String _notOp) {
        super(_notOp);
    }

    protected int getPriority(ILexem l) {
        String val = l.getValue();
        if ("div".equalsIgnoreCase(val) ||
            "mod".equalsIgnoreCase(val))
            return 2;
        else if ("and".equalsIgnoreCase(val) ||
                 "or".equalsIgnoreCase(val) ||
                 "xor".equalsIgnoreCase(val))
            return 5;
        return super.getPriority(l);
    }

    protected ILexem calculateExpr(ILexem op1, ILexem op2, ILexem oper) {
        if ("mod".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant(Double.parseDouble(op1.getValue()) % Double.parseDouble(op2.getValue()) + "", DataType.TYPE_INTEGER, oper.getLine());
        else if ("div".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant(Math.floor(Double.parseDouble(op1.getValue()) / Double.parseDouble(op2.getValue())) + "", DataType.TYPE_INTEGER, oper.getLine());
        else if ("=".equalsIgnoreCase(oper.getValue())) {
            if ("true".equalsIgnoreCase(op1.getValue()) || "false".equalsIgnoreCase(op1.getValue()))
                return ConstantManager.createConstant((Boolean.parseBoolean(op1.getValue()) == Boolean.parseBoolean(op2.getValue())) + "", DataType.TYPE_BOOLEAN, oper.getLine());
            else
                return ConstantManager.createConstant((Double.parseDouble(op1.getValue()) == Double.parseDouble(op2.getValue())) + "", DataType.TYPE_FLOAT, oper.getLine());
        } else if ("<>".equalsIgnoreCase(oper.getValue())) {
            if ("true".equalsIgnoreCase(op1.getValue()) || "false".equalsIgnoreCase(op1.getValue()))
                return ConstantManager.createConstant((Boolean.parseBoolean(op1.getValue()) != Boolean.parseBoolean(op2.getValue())) + "", DataType.TYPE_BOOLEAN, oper.getLine());
            else
                return ConstantManager.createConstant((Double.parseDouble(op1.getValue()) != Double.parseDouble(op2.getValue())) + "", DataType.TYPE_BOOLEAN, oper.getLine());
        } else if ("and".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant((Boolean.parseBoolean(op1.getValue()) && Boolean.parseBoolean(op2.getValue())) + "", DataType.TYPE_BOOLEAN, oper.getLine());
        else if ("or".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant((Boolean.parseBoolean(op1.getValue()) || Boolean.parseBoolean(op2.getValue())) + "", DataType.TYPE_BOOLEAN, oper.getLine());
        else if ("xor".equalsIgnoreCase(oper.getValue())) {
            if (Boolean.parseBoolean(op1.getValue()) && Boolean.parseBoolean(op2.getValue()) ||
                !(Boolean.parseBoolean(op1.getValue()) || Boolean.parseBoolean(op2.getValue())))
                return ConstantManager.createConstant("false", DataType.TYPE_BOOLEAN, oper.getLine());
            else
                return ConstantManager.createConstant("true", DataType.TYPE_BOOLEAN, oper.getLine());
        }
        return super.calculateExpr(op1, op2, oper);
    }
}
