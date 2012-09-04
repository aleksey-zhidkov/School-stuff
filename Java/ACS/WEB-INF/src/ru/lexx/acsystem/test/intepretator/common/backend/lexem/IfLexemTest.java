package ru.lexx.acsystem.test.intepretator.common.backend.lexem;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.ElseLexem;
import ru.lexx.acsystem.interpretator.common.lexem.IKeyWordLexem;
import ru.lexx.acsystem.interpretator.common.lexem.IfLexem;
import ru.lexx.acsystem.interpretator.common.variable.IntegerVariable;
import ru.lexx.acsystem.test.envoirment.PascalSharedData;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 14:59:28
 * To change this template use File | Settings | File Templates.
 */
public class IfLexemTest extends TestCase {
    public IfLexemTest() {
    }

    public IfLexemTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testChangeState() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalSharedData.CONDITION1);
            context.addVariable(new IntegerVariable(3, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertTrue(((ElseLexem) context.getLexem(38)).isSkipping());
            Assert.assertTrue(context.getLexemPointer() == 23);
            context.initContext();
            context.addVariable(new IntegerVariable(4, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertFalse(((ElseLexem) context.getLexem(38)).isSkipping());
            context.incrementPointer();
            Assert.assertTrue(context.getLexemPointer() + context.getCurLexem().getMovePointerShift() == 38);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalSharedData.CONDITION2);
            context.addVariable(new IntegerVariable(2, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertTrue(((ElseLexem) context.getLexem(27)).isSkipping());
            Assert.assertTrue(context.getLexemPointer() == 23);
            context.initContext();
            context.addVariable(new IntegerVariable(4, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertFalse(((ElseLexem) context.getLexem(27)).isSkipping());
            context.incrementPointer();
            Assert.assertTrue(context.getLexemPointer() + context.getCurLexem().getMovePointerShift() == 27);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalSharedData.CONDITION3);
            context.addVariable(new IntegerVariable(2, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertTrue(((ElseLexem) context.getLexem(27)).isSkipping());
            Assert.assertTrue(context.getLexemPointer() == 23);
            context.initContext();
            context.addVariable(new IntegerVariable(4, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertFalse(((ElseLexem) context.getLexem(27)).isSkipping());
            context.incrementPointer();
            Assert.assertTrue(context.getLexemPointer() + context.getCurLexem().getMovePointerShift() == 27);

            context = new ProgramContext(ProgLanguage.PASCAL, PascalSharedData.CONDITION4);
            context.addVariable(new IntegerVariable(2, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertTrue(((ElseLexem) context.getLexem(34)).isSkipping());
            Assert.assertTrue(context.getLexemPointer() == 23);
            context.initContext();
            context.addVariable(new IntegerVariable(4, "i", 0));
            context.addVariable(new IntegerVariable(3, "j", 0));
            while (!(context.getNextLexem() instanceof IfLexem)) ;
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            Assert.assertFalse(((ElseLexem) context.getLexem(34)).isSkipping());
            context.incrementPointer();
            Assert.assertTrue(context.getLexemPointer() + context.getCurLexem().getMovePointerShift() == 34);
        } catch (Exception e) {
            fail(e.toString());
        }
    }

    @Override
    public void runTest() {
        testChangeState();
    }
}
