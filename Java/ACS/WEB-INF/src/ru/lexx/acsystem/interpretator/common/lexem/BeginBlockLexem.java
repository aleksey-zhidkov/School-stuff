// Decompiled by Jad v1.5.7f. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BeginBlockLexem.java

package ru.lexx.acsystem.interpretator.common.lexem;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.interpretator.common.errors.VariableNotDefinedException;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;

public class BeginBlockLexem extends AbstractKeyWordLexem {

    public BeginBlockLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
        context.incSubBlockLevel();

        for (IVariable v : context.getData().getInput_data()) {
            try {
                context.setVariableValue(v.getName(), v.getVarValue());
            }
            catch (UserErrorException e) {
                if (e instanceof VariableNotDefinedException) {
                    throw new VariableNotDefinedException("Входная переменная " + v.getName() + " не определенна", 0);
                }
                String mes = e.getMessage();
                String type = mes.substring(mes.indexOf("'") + 1, mes.lastIndexOf("'"));
                throw new UserErrorException("Несоответсвие типа входного параметра '" + v.getName() + "' - '" + type + "', ожидается '" + v.getType().getCode() + "'", 0);
            }
        }
    }
}
