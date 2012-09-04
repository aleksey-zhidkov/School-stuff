package ru.lexx.acsystem.test.intepretator.common.lexem;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.ElseLexem;
import ru.lexx.acsystem.interpretator.common.lexem.IKeyWordLexem;
import ru.lexx.acsystem.interpretator.common.lexem.IfLexem;
import ru.lexx.acsystem.interpretator.common.variable.IntegerVariable;
import ru.lexx.acsystem.test.envoirment.PascalPrograms;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 18:23:46
 * To change this template use File | Settings | File Templates.
 */
public class ElseLexemTest extends TestCase {
    public ElseLexemTest() {
    }

    public ElseLexemTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testChangeState() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.CONDITION1);
            context.addVariable(new IntegerVariable(3, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            while (!(context.getNextLexem() instanceof ElseLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            context.incrementPointer();
            Assert.assertTrue(context.getLexemPointer() + context.getCurLexem().getMovePointerShift() == 46);
            context.initContext();
            context.addVariable(new IntegerVariable(4, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            while (!(context.getNextLexem() instanceof ElseLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertTrue(context.getLexemPointer() == 38);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.CONDITION2);
            context.addVariable(new IntegerVariable(2, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            while (!(context.getNextLexem() instanceof ElseLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            context.incrementPointer();
            Assert.assertTrue(context.getLexemPointer() + context.getCurLexem().getMovePointerShift() == 39);
            context.initContext();
            context.addVariable(new IntegerVariable(4, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            while (!(context.getNextLexem() instanceof ElseLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertTrue(context.getLexemPointer() == 27);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.CONDITION3);
            context.addVariable(new IntegerVariable(2, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            while (!(context.getNextLexem() instanceof ElseLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            context.incrementPointer();
            Assert.assertTrue(context.getLexemPointer() + context.getCurLexem().getMovePointerShift() == 32);
            context.initContext();
            context.addVariable(new IntegerVariable(4, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            while (!(context.getNextLexem() instanceof ElseLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertTrue(context.getLexemPointer() == 27);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.CONDITION4);
            context.addVariable(new IntegerVariable(2, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            while (!(context.getNextLexem() instanceof ElseLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            context.incrementPointer();
            Assert.assertTrue(context.getLexemPointer() + context.getCurLexem().getMovePointerShift() == 46);
            context.initContext();
            context.addVariable(new IntegerVariable(4, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            while (!(context.getNextLexem() instanceof ElseLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertTrue(context.getLexemPointer() == 34);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Override
    public void runTest() {
        testChangeState();
    }
}
