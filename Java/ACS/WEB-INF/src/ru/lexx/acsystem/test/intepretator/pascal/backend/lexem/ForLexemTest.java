package ru.lexx.acsystem.test.intepretator.pascal.backend.lexem;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.IKeyWordLexem;
import ru.lexx.acsystem.test.envoirment.PascalSharedData;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 16:30:53
 */
public class ForLexemTest extends TestCase {
    public ForLexemTest() {
    }

    public ForLexemTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testFor() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalSharedData.FOR1);
            context.createVariable("integer", "i");
            context.createVariable("integer", "j");
            for (; !"for".equalsIgnoreCase(context.getCurLexem().getValue()); context.incrementPointer())
                ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            context.incrementPointer();
            Assert.assertTrue("1".equalsIgnoreCase(context.getVariable("i").getValue().toString()));
            Assert.assertTrue(context.getLexemPointer() == 18);
            Assert.assertTrue(context.getLexem(23).getMovePointerShift() == -13);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void runTest() {
        testFor();
    }
}
