package ru.lexx.acsystem.interpretator.common;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.constant.ConstantManager;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;
import ru.lexx.acsystem.interpretator.common.lexem.OperatorLexem;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.errors.UnexpectedLexemException;
import ru.lexx.acsystem.interpretator.common.errors.IncompatibleTypesException;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 21:35:33
 */
public class CommonCalculator implements ICalculator {

    protected String NOT_OPERATOR;

    private CommonCalculator() {
    }

    protected CommonCalculator(String _notOp) {
        NOT_OPERATOR = _notOp;
    }

    //todo it's debug method, need delete after realise
    public void printData(Stack<ILexem> stack, List<ILexem> lst) {
        System.out.println("----------------------\nStack:");
        for (ILexem l : stack)
            System.out.println(l.getValue());
        System.out.println("LIst:");
        for (ILexem l : lst)
            System.out.println(l.getValue());
    }

    private String calculateString(ILexem[] lexems) {
        Stack<ILexem> stack = new Stack<ILexem>();
        List<ILexem> lst = getPostfixExpr(lexems);
        validateExpression(lexems);
        for (ILexem lexem : lst) {
            if (lexem.getLexemType() == Lexem.LEXEM_CONSTANT)
                stack.push(lexem);
            else if (NOT_OPERATOR.equalsIgnoreCase(lexem.getValue()))
                stack.push(ConstantManager.createConstant(!Boolean.parseBoolean(stack.pop().getValue()) + "", DataType.TYPE_BOOLEAN, lexem.getLine()));
            else if ("un_min".equalsIgnoreCase(lexem.getValue()))
                stack.push(ConstantManager.createConstant(Double.parseDouble(stack.pop().getValue()) * -1.0d + "", DataType.TYPE_FLOAT, lexem.getLine()));
            else {
                ILexem op1 = stack.pop();
                ILexem op2 = stack.pop();
                stack.push(calculateExpr(op2, op1, lexem));
            }
        }
        return stack.pop().getValue();
    }

    private List<ILexem> getPostfixExpr(ILexem[] lexems) {
        List<ILexem> lst = new ArrayList<ILexem>();
        Stack<ILexem> stack = new Stack<ILexem>();
        for (int i = 0; i < lexems.length; i++) {
            ILexem lexem = lexems[i];
            if (lexem.getLexemType() == Lexem.LEXEM_CONSTANT)
                lst.add(lexem);
            else if (")".equalsIgnoreCase(lexem.getValue())) {
                ILexem l;
                while (!"(".equalsIgnoreCase((l = stack.pop()).getValue())) lst.add(l);
            } else if (stack.empty() || "(".equalsIgnoreCase(lexem.getValue()))
                stack.push(lexem);
            else if ("-".equalsIgnoreCase(lexem.getValue()) && lexems[i - 1].getLexemType() == Lexem.LEXEM_OPERATOR &&
                     lexems[i + 1].getLexemType() == Lexem.LEXEM_CONSTANT) {
                lst.add(new OperatorLexem("un_min", lexem.getLine()));
            } else if (getPriority(lexem) <= getPriority(stack.peek())) {
                while (!stack.empty() && getPriority(lexem) <= getPriority(stack.peek()) && !"(".equals(stack.peek().getValue())) lst.add(stack.pop());
                stack.push(lexem);
            } else if (getPriority(lexem) > getPriority(stack.peek())) {
                stack.push(lexem);
            } else
                lst.add(lexem);
        }

        while (!stack.empty()) lst.add(stack.pop());

        return lst;
    }

    public int calculateInt(ILexem[] lexems) {
        String res = calculateString(lexems);
        if (res.indexOf(".") != -1)
            res = res.substring(0, res.indexOf("."));
        return Integer.parseInt(res);
    }

    public double calculateDouble(ILexem[] lexems) {
        return Double.parseDouble(calculateString(lexems));
    }

    public boolean calculateBoolean(ILexem[] lexems) {
        return Boolean.parseBoolean(calculateString(lexems));
    }

    public void validateExpression(ILexem[] lexems) {
        int b = 0;
        for (ILexem lexem : lexems) {
            if ("(".equalsIgnoreCase(lexem.getValue())) {
                b++;
            }
            if (")".equalsIgnoreCase(lexem.getValue())) {
                b--;
            }
        }
        if (b > 0)
            throw new UserErrorException("Не хватает закрывающей скобки \")\"", lexems[0].getLine());
        if (b < 0)
            throw new UserErrorException("Не хватает открывающей скобки \"(\"", lexems[0].getLine());

        List<ILexem> pref = getPostfixExpr(lexems);
        checkTypes(pref, pref.size() - 1);
    }

    private int checkTypes(List<ILexem> lexems, int pointer) {
        if (lexems.get(pointer).getLexemType() != Lexem.LEXEM_OPERATOR)
            return pointer - 1;
        int p1 = checkTypes(lexems, pointer - 1);

        DataType t1 = getLexemType(lexems.get(pointer - 1));
        DataType t2 = getLexemType(lexems.get(p1));

        if (!DataType.compatible(t1, t2)) {
            throw new IncompatibleTypesException("Несоответсвие типов: ожидается " + t1.getCode() + ", получен: " + t2.getCode(), lexems.get(pointer - 1).getLine());
        }
        return checkTypes(lexems, p1);

    }

    private static DataType getLexemType(ILexem l) {
        if (l instanceof IConstant)
            return ((IConstant) l).getType();
        else if (l instanceof OperatorLexem) {
            if ("+-*/divmod".indexOf(l.getValue().toLowerCase()) != -1) {
                return DataType.TYPE_INTEGER;
            } else if ("orandxor>=<=<>".indexOf(l.getValue().toLowerCase()) != -1) {
                return DataType.TYPE_BOOLEAN;
            }
        }
        throw new UnexpectedLexemException("Неожиданный символ '" + l.getValue() + "' at", l.getLine());
    }

    protected int getPriority(ILexem l) {
        String val = l.getValue();
        if (">=".equalsIgnoreCase(val) ||
            "<=".equalsIgnoreCase(val) ||
            "<".equalsIgnoreCase(val) ||
            ">".equalsIgnoreCase(val) ||
            "=".equalsIgnoreCase(val) ||
            "<>".equalsIgnoreCase(val))
            return 1;
        else if ("+".equalsIgnoreCase(val) ||
                 "-".equalsIgnoreCase(l.getValue()))
            return 2;
        else if ("*".equalsIgnoreCase(val) ||
                 "/".equalsIgnoreCase(val))
            return 3;
        else if ("(".equalsIgnoreCase(val) ||
                 ")".equalsIgnoreCase(val))
            return 7;
        else
            return 0;
    }

    protected ILexem calculateExpr(ILexem op1, ILexem op2, ILexem oper) {
        if ("+".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant(Double.parseDouble(op1.getValue()) + Double.parseDouble(op2.getValue()) + "", DataType.TYPE_FLOAT, oper.getLine());
        else if ("-".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant(Double.parseDouble(op1.getValue()) - Double.parseDouble(op2.getValue()) + "", DataType.TYPE_FLOAT, oper.getLine());
        else if ("*".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant(Double.parseDouble(op1.getValue()) * Double.parseDouble(op2.getValue()) + "", DataType.TYPE_FLOAT, oper.getLine());
        else if ("/".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant(Double.parseDouble(op1.getValue()) / Double.parseDouble(op2.getValue()) + "", DataType.TYPE_FLOAT, oper.getLine());
        else if (">=".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant((Double.parseDouble(op1.getValue()) >= Double.parseDouble(op2.getValue())) + "", DataType.TYPE_BOOLEAN, oper.getLine());
        else if ("<=".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant((Double.parseDouble(op1.getValue()) <= Double.parseDouble(op2.getValue())) + "", DataType.TYPE_BOOLEAN, oper.getLine());
        else if ("<".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant((Double.parseDouble(op1.getValue()) < Double.parseDouble(op2.getValue())) + "", DataType.TYPE_BOOLEAN, oper.getLine());
        else if (">".equalsIgnoreCase(oper.getValue()))
            return ConstantManager.createConstant((Double.parseDouble(op1.getValue()) > Double.parseDouble(op2.getValue())) + "", DataType.TYPE_BOOLEAN, oper.getLine());
        return null;
    }
}
