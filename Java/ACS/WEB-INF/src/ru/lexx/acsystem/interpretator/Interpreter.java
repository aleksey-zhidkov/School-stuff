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
     * ����� ����������� �������� �������
     * @param task �������
     * @param program �������
     * @param lang ���� ����������������
     * @return ���������� <b>true</b> ���� ������� ����� ��� <b>false</b>
     * � ��������� ������
     */
    public boolean interpretate(Task task, String program, ProgLanguage lang) {
        try {
            context = new ProgramContext(lang, program);
        }
        catch (Exception e) {
            SystemManager.getLogger().log((new StringBuilder()).append(getClass().getName()).append('.').toString(), e);
        }

        TaskData[] tasks = task.getData();
        // ���� �� ���� ������� ������ ��� �� ������� ������ �� �������
        // ������� �� �����.
        for (TaskData ctask : tasks) {
            context.initContext();
            context.setTaskData(ctask);
            if (!interpretate())
                /* ���� ������� �� ����� ���� �� ��� ������ ��
                   ������� ������� ������, ������ ��� �� ����� ������.
                */
                return false;
        }
        // ���� ����� �� ����, ������ ������� ������
        return true;
    }

    /**
     * ������������� ������� � ������� ������� ������.
     * @return ���������� <b>true</b> ���� ������� ����� ��� <b>false</b>
     * ���� ������� �� ����� ��� �������� ������.
     */
    private boolean interpretate() {
        try {
            ILexem[] ls =  context.getLexems();
            if (!"end".equalsIgnoreCase(ls[ls.length-2].getValue()) ||
                !   ".".equalsIgnoreCase(ls[ls.length-1].getValue()))
                throw new UserErrorException("��������� 'end.'",context.getLexem(context.getLexems().length-1).getLine());
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
                        throw new UserErrorException("���������� ��� ���� ���������", curLexem.getLine());
                    }
                    parseExpression();
                    context.movePointer(-1);
                } else if (curLexem.getLexemType() == Lexem.LEXEM_FUNCTION) {
                    if (context.getSubBlockLevel() == 0) {
                        throw new UserErrorException("����� ������� ��� ���� ���������", curLexem.getLine());
                    }
                    context.getParser().parseFunction(context, curLexem);
                }
            }
            if (context.getSubBlockLevel() > 0)
                throw new UserErrorException("�� ������� 'end;'", context.getLexemByShift(-1).getLine());
            boolean res = true;
            List<IVariable> data = context.getData().getOutput_data();
            for (IVariable v : data) {
                IVariable v1;
                try {
                    v1 = context.getVariable(v.getName());
                }
                catch (UserErrorException e) {
                    throw new UserErrorException("�� ����������� �������� ���������� '" + v.getName() + "'", 0);
                }
                res &= v.equalsTo(context.getVarManager().cast(v1, v.getType()), new Double(REAL_ACCURACY));
            }

            return res;
        }
        catch (UserErrorException e) {
            exception = e;
            return false;
        } catch(ArrayIndexOutOfBoundsException e) {
            exception = new UserErrorException("��������� 'end.'",context.getLexem(context.getLexems().length-1).getLine());
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
            throw new UserErrorException("����������� ������ '" + l.getValue() + "', ��������� ����������", l.getLine());
        }
    }

    public UserErrorException getException() {
        return exception;
    }
}
