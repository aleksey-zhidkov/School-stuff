package ru.lexx.acsystem.test.intepretator.pascal.lexem;

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
 * Time: 14:35:06
 */
public class UntilLexemTest extends TestCase {
    public UntilLexemTest() {
    }

    public UntilLexemTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testUntil() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.REPEAT1);
            context.createVariable("integer", "i", "5");
            context.createVariable("integer", "j", "10");
            for (; !"until".equalsIgnoreCase(context.getCurLexem().getValue()); context.incrementPointer())
                ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            assertTrue(context.getLexemPointer() == 7);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.REPEAT1);
            context.createVariable("integer", "j", "5");
            context.createVariable("integer", "i", "10");
            for (; !"until".equalsIgnoreCase(context.getCurLexem().getValue()); context.incrementPointer())
                ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            assertTrue(context.getLexemPointer() == 20);
        }
        catch (Exception e) {
            fail(e.toString());
            e.printStackTrace();
        }
    }

    public void runTest() {
        testUntil();
    }
}
