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
 * Time: 11:56:54
 * To change this template use File | Settings | File Templates.
 */
public class SqrFunctionTest extends TestCase {
    public SqrFunctionTest() {
    }

    public SqrFunctionTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testSqr() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, "sqr(2)");
            context.incrementPointer();
            String val = context.getFuncManager().executeFunction(context, context.getLexemByShift(-1)).getValue();
            Assert.assertTrue(4.0d == Double.parseDouble(val));

            context = new ProgramContext(ProgLanguage.PASCAL, "sqr(2.25)");
            context.incrementPointer();
            val = context.getFuncManager().executeFunction(context, context.getLexemByShift(-1)).getValue();
            Assert.assertTrue(5.0625d == Double.parseDouble(val));

            context = new ProgramContext(ProgLanguage.PASCAL, "sqr(1/3)");
            context.incrementPointer();
            val = context.getFuncManager().executeFunction(context, context.getLexemByShift(-1)).getValue();
            Assert.assertTrue(0.1111111111111111d == Double.parseDouble(val));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runTest() {
        testSqr();
    }
}
