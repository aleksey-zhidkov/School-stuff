package ru.lexx.acsystem.test.intepretator.pascal.backend.function;

import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 12:14:52
 * To change this template use File | Settings | File Templates.
 */
public class TruncFunctionTest extends TestCase {
    public TruncFunctionTest() {
    }

    public TruncFunctionTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testTrunc() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, "trunc(1.5)");
            context.incrementPointer();
            String res = context.getFuncManager().executeFunction(context, context.getLexemByShift(-1)).getValue();
            assertTrue("1".equalsIgnoreCase(res));

            context = new ProgramContext(ProgLanguage.PASCAL, "trunc(1 / 3)");
            context.incrementPointer();
            res = context.getFuncManager().executeFunction(context, context.getLexemByShift(-1)).getValue();
            assertTrue("0".equalsIgnoreCase(res));
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void runTest() {
        testTrunc();
    }
}
