package ru.lexx.acsystem.test.intepretator.pascal.backend.function;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 19:07:26
 */
public class SqrtFunctionTest extends TestCase {
    public SqrtFunctionTest() {
    }

    public SqrtFunctionTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testSqrt() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, "sqrt(2)");
            context.incrementPointer();
            String val = context.getFuncManager().executeFunction(context, context.getLexemByShift(-1)).getValue();
            Assert.assertTrue(1.4142135623730951d == Double.parseDouble(val));

            context = new ProgramContext(ProgLanguage.PASCAL, "sqrt(4)");
            context.incrementPointer();
            val = context.getFuncManager().executeFunction(context, context.getLexemByShift(-1)).getValue();
            Assert.assertTrue(2.0d == Double.parseDouble(val));
        }
        catch (Exception e) {
            fail(e.toString());
            e.printStackTrace();
        }
    }

    public void runTest() {
        testSqrt();
    }
}
