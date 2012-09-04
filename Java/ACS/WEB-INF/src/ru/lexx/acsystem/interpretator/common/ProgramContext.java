package ru.lexx.acsystem.interpretator.common;

import ru.jdev.utils.logging.LogMessageLevel;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.TaskData;
import ru.lexx.acsystem.interpretator.common.constant.IntegerConstant;
import ru.lexx.acsystem.interpretator.common.function.IFunctionManager;
import ru.lexx.acsystem.interpretator.common.lexem.*;
import ru.lexx.acsystem.interpretator.common.variable.ArrayVariable;
import ru.lexx.acsystem.interpretator.common.variable.FloatVariable;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;
import ru.lexx.acsystem.interpretator.common.variable.IVariableManager;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.errors.VariableNotDefinedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 01.10.2005
 * Time: 23:02:09
 */
public class ProgramContext {
    private IVariableManager varManager;
    private Map<String, IVariable> variables = new HashMap<String, IVariable>();
    private ILexem[] lexems;
    private int lexemPointer;
    private TaskData data;
    private int subBlockLevel;
    private IFunctionManager funcManager;
    private ICalculator calculator;
    private IParser parser;
    private boolean isVarsDecalred = false;

    public ProgramContext(ProgLanguage lang, String program) throws Exception {
        ProgLangEquipmentBuilder builder = ProgLangEquipmentBuilder.getInstance(lang);
        funcManager = builder.buildFunctionManager();
        calculator = builder.buildCalculator();
        parser = builder.buildParser();
        varManager = builder.buildVariableManager();
        lexems = parser.parse(program);
    }

    public void setTaskData(TaskData _data) {
        data = _data;
    }

    public TaskData getData() {
        return data;
    }

    public ILexem[] getLexems() {
        return lexems;
    }

    public int getLexemPointer() {
        return lexemPointer;
    }

    public void initContext() {
        lexemPointer = 0;
        variables.clear();
        addVariable(new FloatVariable(Math.PI, "pi", 0));
    }

    public void addVariable(IVariable v) {
        variables.put(v.getName(), v);
    }

    public int incrementPointer() {
        return ++lexemPointer;
    }

    public ILexem getCurLexem() {
        return lexems[lexemPointer];
    }

    public IVariable getVariable(String name) {
        IVariable v = variables.get(name);
        if (v == null) {
            int line = lexemPointer < lexems.length ? lexems[lexemPointer].getLine() : 0;
            throw new UserErrorException("Переменная " + name + " не определенна", line);
        }
        return v;
    }

    public void setVariableValue(String name, Object value) {
        IVariable v = variables.get(name);
        if (v == null)
            throw new VariableNotDefinedException("Переменная " + name + " не определенна", lexems[lexemPointer].getLine());
        v.setVarValue(value);
    }

    public IVariable getVariableFromLexem(int i) {
        IVariable v = variables.get(lexems[i].getValue());
        if (v == null)
            throw new UserErrorException("Переменная " + lexems[i].getValue() + " не определенна", lexems[i].getLine());
        return v;
    }

    public ILexem getNextLexem() {
        return lexems[++lexemPointer];
    }

    public void putValueToVariable(IVariable var, String val) {
        varManager.putValueToVariable(var, val);
    }

    public void putValueToVariable(String var, String val) {
        IVariable v = variables.get(var);
        if (v == null)
            throw new UserErrorException("Переменная " + lexems[lexemPointer].getValue() + " не определенна", lexems[lexemPointer].getLine());
        putValueToVariable(v, val);
    }

    public ILexem getLexem(int i) {
        return lexems[i];
    }

    public void printVars() {
        SystemManager.getLogger().log(variables.toString(), LogMessageLevel.LEVEL_DEBUG, getClass().getName() + ".printVars");
    }

    public void createVariable(String type, String name, String value) {
        variables.put(name, varManager.createVariable(varManager.getInternalType(type), name, value));
    }

    public void createVariable(String type, String name) {
        variables.put(name, varManager.createVariable(varManager.getInternalType(type), name));
    }

    public int getBlockLength() {
        int i = lexemPointer + 1;
        int len = 0;
        if (lexems[i] instanceof BeginBlockLexem) {
            int stack = 0;
            while (true) {
                if (lexems[i] instanceof BeginBlockLexem)
                    stack++;
                else if (lexems[i] instanceof EndBlockLexem)
                    stack--;
                i++;
                len++;
                if (stack == 0) {
                    if (";".equalsIgnoreCase(lexems[i].getValue()))
                        len++;
                    break;
                }
            }
        } else {
            int stack = 0;
            while (true) {
                if (lexems[i] instanceof IfLexem)
                    stack++;
                else if (lexems[i] instanceof ElseLexem)
                    stack--;
                i++;
                len++;
                if (stack == -1 || (stack == 0 && ";".equalsIgnoreCase(lexems[i].getValue()))) {
                    len++;
                    break;
                }
            }
        }
        return len;
    }

    public ILexem getLexemByShift(int shift) throws ArrayIndexOutOfBoundsException {
        if (lexemPointer + shift < 0)
            throw new ArrayIndexOutOfBoundsException(lexemPointer + shift);
        if (lexemPointer + shift >= lexems.length)
            throw new ArrayIndexOutOfBoundsException(lexemPointer + shift);
        return lexems[lexemPointer + shift];
    }

    public void movePointer(int shift) throws ArrayIndexOutOfBoundsException {
        if (lexemPointer + shift < 0)
            throw new ArrayIndexOutOfBoundsException(lexemPointer + shift);
        if (lexemPointer + shift >= lexems.length)
            throw new ArrayIndexOutOfBoundsException(lexemPointer + shift);
        lexemPointer += shift;
    }

    public int incSubBlockLevel() {
        return ++subBlockLevel;
    }

    public int decSubBlockLevel() {
        return --subBlockLevel;
    }

    public int getSubBlockLevel() {
        return subBlockLevel;
    }

    public IFunctionManager getFuncManager() {
        return funcManager;
    }

    public ICalculator getCalculator() {
        return calculator;
    }

    public IParser getParser() {
        return parser;
    }

    public IVariableManager getVarManager() {
        return varManager;
    }

    public IVariable getVariableFromArray(String arrName, List<IntegerConstant> indexes) {
        return getVariableFromArray((ArrayVariable) getVariable(arrName), indexes);
    }

    public IVariable getVariableFromArray(ArrayVariable arr, List<IntegerConstant> indexes) {
        if (indexes.size() == 1)
            return arr.get(Integer.parseInt(indexes.get(0).getValue()));
        indexes.remove(0);
        return getVariableFromArray((ArrayVariable) arr.get(Integer.parseInt(indexes.get(0).getValue())), indexes);
    }

    public boolean isVarsDeclared() {
        return isVarsDecalred;
    }

    public void declareVars() {
        isVarsDecalred = true;
    }

    public int getCurLine() {
        return lexems[lexemPointer].getLine();
    }

    public boolean hasMoreLexems() {
        return lexemPointer < lexems.length - 1;
    }
}
