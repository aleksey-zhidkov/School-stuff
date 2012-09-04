package ru.lexx.acsystem.interpretator.pascal.function;

import ru.lexx.acsystem.backend.constants.DataType;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.interpretator.common.errors.NoSuchFunctionException;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.function.IFunction;
import ru.lexx.acsystem.interpretator.common.function.IFunctionManager;
import ru.lexx.acsystem.interpretator.common.function.VoidFunction;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 01.10.2005
 * Time: 22:23:55
 */
public class PascalFunctionManager implements IFunctionManager {

    private HashMap<String, IFunction> FUNCTIONS_SET = new HashMap<String, IFunction>();


    public PascalFunctionManager(ProgLanguage lang) {
        if (lang == ProgLanguage.PASCAL) {
            FUNCTIONS_SET = new HashMap<String, IFunction>();
            FUNCTIONS_SET.put("trunc", new TruncFunction());
            FUNCTIONS_SET.put("frac", new FracFunction());
            FUNCTIONS_SET.put("str", new StrFunction());
            FUNCTIONS_SET.put("writeln", new VoidFunction());
            FUNCTIONS_SET.put("read", new VoidFunction());
            FUNCTIONS_SET.put("readln", new VoidFunction());
            FUNCTIONS_SET.put("sqr", new SqrFunction());
            FUNCTIONS_SET.put("inc", new IncFunction());
            FUNCTIONS_SET.put("sqrt", new SqrtFunction());
        }
    }

    public boolean isFunction(String name) {
        return FUNCTIONS_SET.get(name.toLowerCase()) != null;
    }

    public IConstant executeFunction(ProgramContext context, ILexem function) {
        IFunction f = FUNCTIONS_SET.get(function.getValue().toLowerCase());
        if (f == null)
            throw new UserErrorException("Неизвестная функция '" + function.getValue() + "'", function.getLine());
        return f.execute(context);
    }

    public DataType getFunctionType(String name) {
        IFunction f = FUNCTIONS_SET.get(name);
        if (f == null) {
            throw new NoSuchFunctionException("Неизвестная функция '" + name + "'", 0);
        }
        return f.getReturnType();
    }
}