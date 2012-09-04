package ru.lexx.acsystem.test.intepretator.common.lexem;

import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.IKeyWordLexem;
import ru.lexx.acsystem.test.envoirment.PascalPrograms;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 12:44:41
 * To change this template use File | Settings | File Templates.
 */
public class WhileLexemTest extends TestCase {
    public WhileLexemTest() {
    }

    public WhileLexemTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testWhile() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.WHILE1);
            context.createVariable("integer", "i", "5");
            context.createVariable("integer", "j", "10");
            for (; !"while".equalsIgnoreCase(context.getCurLexem().getValue()); context.incrementPointer())
                ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            context.incrementPointer();
            assertTrue(context.getLexemPointer() == 14);
            assertTrue(context.getCurLexem().getMovePointerShift() == 0);
            assertTrue(context.getLexem(20).getMovePointerShift() == -13);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.WHILE1);
            context.createVariable("integer", "i", "10");
            context.createVariable("integer", "j", "10");
            for (; !"while".equalsIgnoreCase(context.getCurLexem().getValue()); context.incrementPointer())
                ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            context.incrementPointer();
            assertTrue(context.getLexemPointer() == 20);
            assertTrue(context.getCurLexem().getMovePointerShift() == 0);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void runTest() {
        testWhile();
    }
}
