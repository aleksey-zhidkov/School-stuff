package ru.lexx.acsystem.test.intepretator.common;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.interpretator.common.ProgramContext;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 21:31:40
 */
public class CalculatorTest extends TestCase {
    public CalculatorTest() {
    }

    public CalculatorTest(String s) {
        super(s);
//        SystemLoader l = new SystemLoader();
    }

    public void testNumericCalculator() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, "4 + 3 .");
            double res = context.getCalculator().calculateDouble(context.getLexems());
            Assert.assertTrue(res == 7.0d);

            context = new ProgramContext(ProgLanguage.PASCAL, "4 + (3 * 2).");
            res = context.getCalculator().calculateDouble(context.getLexems());
            Assert.assertTrue(res == 10.0d);

            context = new ProgramContext(ProgLanguage.PASCAL, "5 div 2.");
            res = context.getCalculator().calculateInt(context.getLexems());
            Assert.assertTrue(res == 2);

            context = new ProgramContext(ProgLanguage.PASCAL, "5 div 2 * 3.");
            res = context.getCalculator().calculateDouble(context.getLexems());
            Assert.assertTrue(res == 6.0d);

            context = new ProgramContext(ProgLanguage.PASCAL, "4 - 5 * 2.");
            res = context.getCalculator().calculateDouble(context.getLexems());
            Assert.assertTrue(res == -6.0d);

            context = new ProgramContext(ProgLanguage.PASCAL, "2 = 2.");
            boolean resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertTrue(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "(2 = 2) and (3 = 2).");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertFalse(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "not((2 = 2) and (3 = 2)).");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertTrue(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "(2 = 2) and not(3 = 2).");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertTrue(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "true and true.");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertTrue(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "true and false.");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertFalse(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "true or false.");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertTrue(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "true xor false.");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertTrue(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "false xor true.");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertTrue(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "false xor false.");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertFalse(resb);

            context = new ProgramContext(ProgLanguage.PASCAL, "true xor true.");
            resb = context.getCalculator().calculateBoolean(context.getLexems());
            Assert.assertFalse(resb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runTest() {
        testNumericCalculator();
    }
}
