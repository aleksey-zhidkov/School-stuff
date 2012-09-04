package ru.lexx.acsystem.interpretator;

import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.Task;
import ru.lexx.acsystem.backend.task.TaskData;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.lexem.IKeyWordLexem;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;
import ru.lexx.acsystem.interpretator.common.variable.BooleanVariable;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;
import ru.lexx.acsystem.interpretator.common.variable.NumericVariable;
import ru.lexx.acsystem.interpretator.common.variable.StringVariable;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;

public class Interpreter {

    private ProgramContext context;
    private static final double REAL_ACCURACY = 0.0001F;
    public static final DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance();
    private UserErrorException exception;

    static {
        DecimalFormatSymbols s = new DecimalFormatSymbols();
        s.setDecimalSeparator('.');
        format.setDecimalFormatSymbols(s);
        format.setMaximumFractionDigits(20);
        format.setMaximumIntegerDigits(20);
        format.setGroupingUsed(false);
    }

    /**
     * Метод выполняющий проверку задания
     * @param task Задание
     * @param program Решение
     * @param lang Язык программирования
     * @return возвращяет <b>true</b> если решение верно или <b>false</b>
     * в противном случае
     */
    public boolean interpretate(Task task, String program, ProgLanguage lang) {
        try {
            context = new ProgramContext(lang, program);
        }
        catch (Exception e) {
            SystemManager.getLogger().log((new StringBuilder()).append(getClass().getName()).append('.').toString(), e);
        }

        TaskData[] tasks = task.getData();
        // цикл по всем наборам данных или до первого набора на котором
        // решение не верно.
        for (TaskData ctask : tasks) {
            context.initContext();
            context.setTaskData(ctask);
            if (!interpretate())
                /* Если решение не верно хотя бы для одного из
                   наборов входных данных, значит оно не верно вообще.
                */
                return false;
        }
        // Если дошли до сюда, значит решение верное
        return true;
    }

    /**
     * Интерпретация решения с текущим набором данных.
     * @return возвращяет <b>true</b> если решение верно или <b>false</b>
     * если решение не верно или содержит ошибки.
     */
    private boolean interpretate() {
        try {
            ILexem[] ls =  context.getLexems();
            if (!"end".equalsIgnoreCase(ls[ls.length-2].getValue()) ||
                !   ".".equalsIgnoreCase(ls[ls.length-1].getValue()))
                throw new UserErrorException("Ожидается 'end.'",context.getLexem(context.getLexems().length-1).getLine());
            for (; context.getLexemPointer() < context.getLexems().length; context.incrementPointer()) {
                ILexem curLexem = context.getCurLexem();
                while (curLexem.getMovePointerShift() != 0) {
                    context.movePointer(curLexem.getMovePointerShift());
                    curLexem = context.getCurLexem();
                }
                if (curLexem.getLexemType() == Lexem.LEXEM_KEYWORD)
                    ((IKeyWordLexem) curLexem).changeState(context);
                else if (":=".equals(curLexem.getValue())) {
                    if (context.getSubBlockLevel() == 0) {
                        throw new UserErrorException("Присвоение вне тела программы", curLexem.getLine());
                    }
                    parseExpression();
                    context.movePointer(-1);
                } else if (curLexem.getLexemType() == Lexem.LEXEM_FUNCTION) {
                    if (context.getSubBlockLevel() == 0) {
                        throw new UserErrorException("Вызов функции вне тела программы", curLexem.getLine());
                    }
                    context.getParser().parseFunction(context, curLexem);
                }
            }
            if (context.getSubBlockLevel() > 0)
                throw new UserErrorException("Не хватает 'end;'", context.getLexemByShift(-1).getLine());
            boolean res = true;
            List<IVariable> data = context.getData().getOutput_data();
            for (IVariable v : data) {
                IVariable v1;
                try {
                    v1 = context.getVariable(v.getName());
                }
                catch (UserErrorException e) {
                    throw new UserErrorException("Не определенна выходная переменная '" + v.getName() + "'", 0);
                }
                res &= v.equalsTo(context.getVarManager().cast(v1, v.getType()), new Double(REAL_ACCURACY));
            }

            return res;
        }
        catch (UserErrorException e) {
            exception = e;
            return false;
        } catch(ArrayIndexOutOfBoundsException e) {
            exception = new UserErrorException("Ожидается 'end.'",context.getLexem(context.getLexems().length-1).getLine());
            return false;
        }
    }

    private void parseExpression() {
        ILexem l = context.getVariableFromLexem(context.getLexemPointer() - 1);

        if (l instanceof NumericVariable)
            context.getParser().doAssigment(context);
        else if (l instanceof StringVariable)
            context.getParser().parseStringExpression(context);
        else if (l instanceof BooleanVariable)
            context.getParser().parseBooleanExpression(context);
        else {
            throw new UserErrorException("Неожиданный символ '" + l.getValue() + "', ожидается переменная", l.getLine());
        }
    }

    public UserErrorException getException() {
        return exception;
    }
}
