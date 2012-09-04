package ru.lexx.acsystem.interpretator.common;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.Interpreter;
import ru.lexx.acsystem.interpretator.common.constant.ConstantManager;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.lexem.*;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CommonParser implements IParser {
    private String DELIMITERS;
    protected int line = 1;

    private CommonParser() {
    }

    protected CommonParser(String _delims) {
        DELIMITERS = _delims;
    }

    public ILexem[] parse(String program)
            throws Exception {
        StringReader reader = new StringReader(program);
        ArrayList<ILexem> lexems = new ArrayList<ILexem>();
        StringBuffer curLexem = new StringBuffer();
        int cch;
        while ((cch = reader.read()) != -1) {
            if (cch == '\n') {
                line++;
            }
            if (DELIMITERS.indexOf(cch) != -1) {
                parseLexem(curLexem.toString(), (char) cch, lexems, reader);
                curLexem = new StringBuffer();
            } else {
                curLexem.append((char) cch);
            }
        }
        if (curLexem.length() > 0)
            parseLexem(curLexem.toString(), ' ', lexems, reader);
        return lexems.toArray(new ILexem[lexems.size()]);
    }

    protected void parseLexem(String lexem, char delim, List<ILexem> lexems, StringReader reader)
            throws Exception {
        /* parsing keywords*/
        if ("if".equalsIgnoreCase(lexem))
            lexems.add(new IfLexem(lexem, line));
        else if ("else".equalsIgnoreCase(lexem))
            lexems.add(new ElseLexem(lexem, line));
        else if ("while".equalsIgnoreCase(lexem))
            lexems.add(new WhileLexem(lexem, line));
        else if (lexem.trim().length() > 0) {
            if (delim == '(') /*if deilm = '(', then next lexem is function*/ {
                lexems.add(new FunctionLexem(lexem, line));
                lexems.add(new OperatorLexem(delim + "", line));
            } else if (Character.isDigit(lexem.charAt(0))) { /*if lexem begins from digit, then it's numeric constant*/
                StringBuffer sb = new StringBuffer(lexem);
                if (delim == '.') {
                    sb.append(delim);
                    int cch;
                    while (DELIMITERS.indexOf(cch = reader.read()) == -1 && cch != -1) {
                        sb.append((char) cch);
                        reader.mark(1);
                    }
                    reader.reset();
                }
                lexems.add(ConstantManager.createConstant(sb.toString(), line));
            } else /* else it's anything defined by user*/
                lexems.add(new UsersDefinedLexem(lexem, line));
        }
        /* parsing operators*/
        if (delim == '(' && lexem.trim().length() == 0 ||
            delim == ')' || delim == ';' ||
            delim == ',' || delim == '+' ||
            delim == '*' || delim == '/' ||
            delim == '-' || delim == '=' ||
            delim == '[' || delim == ']')
            lexems.add(new OperatorLexem(delim + "", line));
    }

    public void doAssigment(ProgramContext context) {
        int targ = context.getLexemPointer() - 1;
        ILexem l = context.getCurLexem();
        IVariable v = context.getVariableFromLexem(targ);
        IConstant c = parseNumericExpression(context, v.getType());
        context.movePointer(-1);
        context.putValueToVariable(v, c.getValue());
    }

    public IConstant parseNumericExpression(ProgramContext context, DataType type) {
        int line = context.getCurLine();
        List<ILexem> lst = repalceVariables(context);
        ILexem[] lexems = new ILexem[lst.size()];
        return ConstantManager.createConstant(Interpreter.format.format(context.getCalculator().calculateDouble(lst.toArray(lexems))), type, line);
    }

    public String parseFunction(ProgramContext context, ILexem lexem) {
        context.incrementPointer();
        if (!context.getFuncManager().isFunction(lexem.getValue()))
            throw new UserErrorException("Неизвесная функция " + lexem.getValue(), lexem.getLine());
        return context.getFuncManager().executeFunction(context, lexem).getValue();
    }

    public void parseStringExpression(ProgramContext context) {
        StringBuffer expr = new StringBuffer();
        ILexem lexem;
        int targ = context.getLexemPointer() - 1;
        do {
            context.incrementPointer();
            lexem = context.getCurLexem();
            if (";".equals(lexem.getValue()))
                break;
            if (lexem.getLexemType() == Lexem.LEXEM_CONSTANT) {
                expr.append(lexem.getValue());
            } else if (lexem.getLexemType() == Lexem.LEXEM_VARIABLE) {
                IVariable v = context.getVariable(lexem.getValue());
                expr.append(v.getValue().toString());
            } else if (lexem.getLexemType() == Lexem.LEXEM_FUNCTION) {
                if (context.getFuncManager().isFunction(lexem.getValue()))
                    expr.append(parseFunction(context, lexem));
            }
        }
        while (true);
        IVariable v = context.getVariableFromLexem(targ);
        context.putValueToVariable(v, expr.toString());
    }

    public IConstant parseParameters(ProgramContext context, DataType dtype) {
        int line = context.getCurLine();
        List<ILexem> lst = new ArrayList<ILexem>();
        int pc = 1;
        do {
            ILexem lexem = context.getNextLexem();
            if (lexem.getLexemType() != Lexem.LEXEM_OPERATOR && context.getLexemByShift(1).getLexemType() != Lexem.LEXEM_OPERATOR)
                throw new UserErrorException("Ожидается ';'", lexem.getLine());
            if ("(".equals(lexem.getValue())) {
                pc++;
                continue;
            }
            if (")".equals(lexem.getValue())) {
                pc--;
                continue;
            }
            Lexem type = lexem.getLexemType();
            if (type == Lexem.LEXEM_CONSTANT || type == Lexem.LEXEM_OPERATOR)
                lst.add(lexem);
            else if (type == Lexem.LEXEM_VARIABLE) {
                IVariable v = context.getVariable(lexem.getValue());
                lst.add(ConstantManager.createConstant(v.getVarValue().toString(), context.getVariable(lexem.getValue()).getType(), lexem.getLine()));
            } else if (type == Lexem.LEXEM_FUNCTION)
                lst.add(ConstantManager.createConstant(parseFunction(context, lexem), context.getFuncManager().getFunctionType(lexem.getValue()), lexem.getLine()));
        }
        while (pc > 0);
        ILexem[] lexems = new ILexem[lst.size()];
        lst.toArray(lexems);
        if (dtype == DataType.TYPE_INTEGER)
            return ConstantManager.createConstant(context.getCalculator().calculateInt(lexems) + "", DataType.TYPE_INTEGER, line);
        else if (dtype == DataType.TYPE_FLOAT)
            return ConstantManager.createConstant(context.getCalculator().calculateDouble(lexems) + "", DataType.TYPE_FLOAT, line);
        throw new IllegalArgumentException("Unknown data type: " + dtype.getName());
    }

    public boolean parseBooleanExpression(ProgramContext context) {
        List<ILexem> lst = repalceVariables(context);
        ILexem[] lexems = new ILexem[lst.size()];
        return context.getCalculator().calculateBoolean(lst.toArray(lexems));
    }

    private List<ILexem> repalceVariables(ProgramContext context) {
        List<ILexem> lst = new ArrayList<ILexem>();
        int stack = 0;
        ILexem lexem = context.getNextLexem();
        boolean isBeginFromP = false;
        if ("(".equalsIgnoreCase(lexem.getValue()))
            isBeginFromP = true;
        do {
            if (isBeginFromP && "(".equalsIgnoreCase(lexem.getValue()))
                stack++;
            else if (isBeginFromP && ")".equalsIgnoreCase(lexem.getValue()))
                stack--;
            if (isBeginFromP && stack == 0 && context.getLexemByShift(1).getLexemType() != Lexem.LEXEM_OPERATOR)
                isBeginFromP = false;
            else
                isBeginFromP = true;
            if ((lexem.getLexemType() == Lexem.LEXEM_CONSTANT || lexem.getLexemType() == Lexem.LEXEM_VARIABLE) &&
                (context.getLexemByShift(1).getLexemType() == Lexem.LEXEM_CONSTANT || context.getLexemByShift(1).getLexemType() == Lexem.LEXEM_VARIABLE))
                throw new UserErrorException("Ожидается ';'", lexem.getLine());
            if (";".equals(lexem.getValue()))
                break;
            if (lexem.getLexemType() == Lexem.LEXEM_OPERATOR || lexem.getLexemType() == Lexem.LEXEM_CONSTANT) {
                lst.add(lexem);
            } else if (lexem.getLexemType() == Lexem.LEXEM_VARIABLE) {
                IVariable v = context.getVariable(lexem.getValue());
                lst.add(ConstantManager.createConstant(v.getVarValue().toString(), context.getVariable(lexem.getValue()).getType(), lexem.getLine()));
            } else if (lexem.getLexemType() == Lexem.LEXEM_FUNCTION) {
                if (context.getFuncManager().isFunction(lexem.getValue()))
                    lst.add(ConstantManager.createConstant(parseFunction(context, lexem), context.getFuncManager().getFunctionType(lexem.getValue()), lexem.getLine()));
            }
            if (isBeginFromP && stack == 0 && context.getLexemByShift(1).getLexemType() != Lexem.LEXEM_OPERATOR || ";".equals(lexem.getValue()) || lexem.getLexemType() == Lexem.LEXEM_KEYWORD) {
                break;
            }
            lexem = context.getNextLexem();
        }
        while (true);
        context.incrementPointer();
        return lst;
    }
}
