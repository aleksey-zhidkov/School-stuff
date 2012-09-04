package ru.lexx.acsystem.interpretator.pascal.lexem;

import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.AbstractKeyWordLexem;
import ru.lexx.acsystem.interpretator.common.variable.ArrayVariable;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 07.11.2005
 * Time: 13:56:19
 */
public class ArrayLexem extends AbstractKeyWordLexem {

    public ArrayLexem(String _value, int _line) {
        super(_value, _line);
    }

    public void changeState(ProgramContext context) {
        int myPos = context.getLexemPointer();
        List<Integer> bis = new ArrayList<Integer>();
        List<Integer> eis = new ArrayList<Integer>();
        List<String> nms = new ArrayList<String>();
        while (!"]".equalsIgnoreCase(context.getNextLexem().getValue())) {
            bis.add(new Integer(context.getNextLexem().getValue()));
            context.incrementPointer();
            eis.add(new Integer(context.getNextLexem().getValue()));
        }
        context.incrementPointer();
        for (int i = myPos - 1; context.getLexem(i).getLexemType() == Lexem.LEXEM_VARIABLE ||
                                ",".equalsIgnoreCase(context.getLexem(i).getValue()); i--)
            if (context.getLexem(i).getLexemType() == Lexem.LEXEM_VARIABLE)
                nms.add(context.getLexem(i).getValue());
        createArray(bis, eis, nms, 0, context, context.getNextLexem().getValue());
    }

    private IVariable createArray(List<Integer> bis, List<Integer> eis, List<String> nms, int deph, ProgramContext context, String type) {
        if (deph == bis.size() + 1) {
            return context.getVarManager().createVariable(context.getVarManager().getInternalType(type), "");
        } else if (deph == 0) {
            for (String s : nms) {
                ArrayVariable var = new ArrayVariable(bis.get(0), eis.get(0), s, 0);
                for (int i = bis.get(0); i <= eis.get(0); i++) {
                    var.set(i, createArray(bis, eis, nms, 2, context, type));
                }
                context.addVariable(var);
            }
        } else {
            ArrayVariable var = new ArrayVariable(bis.get(deph - 1), eis.get(deph - 1), "", 0);
            for (int i = bis.get(deph - 1); i <= eis.get(deph - 1); i++)
                var.set(i, createArray(bis, eis, nms, deph + 1, context, type));
            return var;
        }
        return null;
    }
}
