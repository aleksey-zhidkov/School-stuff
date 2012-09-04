package ru.lexx.acsystem.test.intepretator.pascal.function;

import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 12:02:30
 * To change this template use File | Settings | File Templates.
 */
public class StrFunctionTest extends TestCase {
    public StrFunctionTest() {
    }

    public StrFunctionTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testStr() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, "str(2.5,s);");
            context.createVariable("string", "s");
            context.incrementPointer();
            context.getFuncManager().executeFunction(context, context.getLexemByShift(-1));
            assertTrue("2.5".equalsIgnoreCase(context.getVariable("s").getValue().toString()));

            context = new ProgramContext(ProgLanguage.PASCAL, "str(2.5 - 1.5,s);");
            context.createVariable("string", "s");
            context.incrementPointer();
            context.getFuncManager().executeFunction(context, context.getLexemByShift(-1));
            assertTrue("1.0".equalsIgnoreCase(context.getVariable("s").getValue().toString()));

            context = new ProgramContext(ProgLanguage.PASCAL, "str(3 * 2,s);");
            context.createVariable("string", "s");
            context.incrementPointer();
            context.getFuncManager().executeFunction(context, context.getLexemByShift(-1));
            assertTrue("6".equalsIgnoreCase(context.getVariable("s").getValue().toString()));
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void runTest() {
        testStr();
    }
}
