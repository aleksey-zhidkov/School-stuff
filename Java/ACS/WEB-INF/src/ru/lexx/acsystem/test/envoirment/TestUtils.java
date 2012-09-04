package ru.lexx.acsystem.test.envoirment;

import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.ILexem;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 20:06:23
 * To change this template use File | Settings | File Templates.
 */
public class TestUtils {

    public static void printLexems(ProgramContext context) {
        ILexem[] lexems = context.getLexems();
        for (int i = 0; i < lexems.length; i++)
            System.out.println("[" + i + "] : " + lexems[i].getValue() + " : " + lexems[i].getLexemType().getName());
    }
}
