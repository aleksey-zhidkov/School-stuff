package ru.lexx.acsystem.test.intepretator.pascal.function;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.test.envoirment.PascalPrograms;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 15:29:57
 */
public class IncFunctionTest extends TestCase {
    public IncFunctionTest() {
    }

    public IncFunctionTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testInc() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.FOR1);
            context.createVariable("integer", "j", "5");
            while (!"inc".equalsIgnoreCase(context.getNextLexem().getValue())) ;
            context.incrementPointer();
            context.getFuncManager().executeFunction(context, context.getLexemByShift(-1));
            Assert.assertTrue(context.getVariable("j").getValue().toString().equalsIgnoreCase("6"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runTest() {
        testInc();
    }
}
