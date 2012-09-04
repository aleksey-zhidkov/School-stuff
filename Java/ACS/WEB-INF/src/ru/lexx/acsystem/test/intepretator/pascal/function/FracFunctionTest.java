package ru.lexx.acsystem.test.intepretator.pascal.function;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 0:43:13
 * To change this template use File | Settings | File Templates.
 */
public class FracFunctionTest extends TestCase {
    public FracFunctionTest() {
    }

    public FracFunctionTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testFract() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, "frac(5.6)");
            context.incrementPointer();
            String val = context.getFuncManager().executeFunction(context, context.getLexemByShift(-1)).getValue();
            Assert.assertTrue(0.6d == Double.parseDouble(val));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runTest() {
        testFract();
    }
}
