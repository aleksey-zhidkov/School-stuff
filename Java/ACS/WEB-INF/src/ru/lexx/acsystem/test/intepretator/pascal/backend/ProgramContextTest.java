package ru.lexx.acsystem.test.intepretator.pascal.backend;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.test.envoirment.PascalSharedData;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 14:45:08
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings({"MagicNumber", "ReuseOfLocalVariable"})
public class ProgramContextTest extends TestCase {

    public ProgramContextTest() {
    }

    public ProgramContextTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testGetBlockLength() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalSharedData.CONDITION1);
            for (; context.getLexemPointer() < 23; context.incrementPointer())
                ;
            int len = context.getBlockLength();
            Assert.assertTrue(context.getLexemPointer() + len + 1 == 38);
            context.movePointer(len + 1);
            len = context.getBlockLength();
            Assert.assertTrue(context.getLexemPointer() + len + 1 == 46);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalSharedData.CONDITION2);
            for (; context.getLexemPointer() < 23; context.incrementPointer())
                ;
            len = context.getBlockLength();
            Assert.assertTrue(context.getLexemPointer() + len + 1 == 27);
            context.movePointer(len + 1);
            len = context.getBlockLength();
            Assert.assertTrue(context.getLexemPointer() + len + 1 == 39);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalSharedData.CONDITION3);
            for (; context.getLexemPointer() < 23; context.incrementPointer())
                ;
            len = context.getBlockLength();
            Assert.assertTrue(context.getLexemPointer() + len + 1 == 27);
            context.movePointer(len + 1);
            len = context.getBlockLength();
            Assert.assertTrue(context.getLexemPointer() + len + 1 == 32);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalSharedData.CONDITION4);
            for (; context.getLexemPointer() < 23; context.incrementPointer())
                ;
            len = context.getBlockLength();
            Assert.assertTrue(context.getLexemPointer() + len + 1 == 34);
            context.movePointer(len + 1);
            len = context.getBlockLength();
            Assert.assertTrue(context.getLexemPointer() + len + 1 == 46);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    public void runTest() {
        testGetBlockLength();
    }
}
