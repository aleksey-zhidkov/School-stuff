package ru.lexx.acsystem.test.intepretator.pascal;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.Lexem;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.constant.IConstant;
import ru.lexx.acsystem.interpretator.common.lexem.*;
import ru.lexx.acsystem.interpretator.pascal.PascalParser;
import ru.lexx.acsystem.interpretator.pascal.lexem.ArrayLexem;
import ru.lexx.acsystem.interpretator.pascal.lexem.OfLexem;
import ru.lexx.acsystem.interpretator.pascal.lexem.ThenLexem;
import ru.lexx.acsystem.test.envoirment.PascalPrograms;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 13:03:42
 */
public class PascalParserTest extends TestCase {

    public PascalParserTest() {
    }

    public PascalParserTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testIfParse() {
        try {
            ILexem[] lexems = new PascalParser().parse(PascalPrograms.CONDITION1);
            /*for(ILexem l : lexeme_analyser)
            System.out.println(l.getValue());*/
            Assert.assertTrue(lexems[17] instanceof IfLexem);
            Assert.assertTrue("if".equalsIgnoreCase(lexems[17].getValue()));
            Assert.assertTrue(lexems[20] instanceof OperatorLexem);
            Assert.assertTrue("=".equalsIgnoreCase(lexems[20].getValue()));
            Assert.assertTrue(lexems[23] instanceof ThenLexem);
            Assert.assertTrue("then".equalsIgnoreCase(lexems[23].getValue()));
            Assert.assertTrue(lexems[38] instanceof ElseLexem);
            Assert.assertTrue("else".equalsIgnoreCase(lexems[38].getValue()));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    public void testParseBooleanExpression() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, ";(i = j);");
            context.createVariable("integer", "i", "3");
            context.createVariable("integer", "j", "3");
            Assert.assertTrue(context.getParser().parseBooleanExpression(context));
            context.movePointer(-6);
            context.setVariableValue("i", new Integer(2));
            Assert.assertFalse(context.getParser().parseBooleanExpression(context));

            context = new ProgramContext(ProgLanguage.PASCAL, ";(i < j);");
            context.createVariable("integer", "i", "3");
            context.createVariable("integer", "j", "4");
            Assert.assertTrue(context.getParser().parseBooleanExpression(context));
            context.movePointer(-6);
            context.setVariableValue("i", new Integer(4));
            Assert.assertFalse(context.getParser().parseBooleanExpression(context));

            context = new ProgramContext(ProgLanguage.PASCAL, ";i = j;");
            context.createVariable("integer", "i", "3");
            context.createVariable("integer", "j", "4");
            Assert.assertFalse(context.getParser().parseBooleanExpression(context));
            context.movePointer(-4);
            context.setVariableValue("i", new Integer(4));
            Assert.assertTrue(context.getParser().parseBooleanExpression(context));

            context = new ProgramContext(ProgLanguage.PASCAL, ";i = 4;");
            context.createVariable("integer", "i", "3");
            Assert.assertFalse(context.getParser().parseBooleanExpression(context));
            context.movePointer(-4);
            context.setVariableValue("i", new Integer(4));
            Assert.assertTrue(context.getParser().parseBooleanExpression(context));

            context = new ProgramContext(ProgLanguage.PASCAL, "true;");
            Assert.assertTrue(context.getCurLexem().getLexemType() == Lexem.LEXEM_CONSTANT);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }
    }

    public void testDoAssign() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, "i := 4 mod 3;");
            context.createVariable("integer", "i");
            context.incrementPointer();
            context.getParser().doAssigment(context);
            Assert.assertTrue("1".equalsIgnoreCase(context.getVariable("i").getValue().toString()));

            context = new ProgramContext(ProgLanguage.PASCAL, "i := 5 div 2;");
            context.createVariable("integer", "i");
            context.incrementPointer();
            context.getParser().doAssigment(context);
            Assert.assertTrue("2".equalsIgnoreCase(context.getVariable("i").getValue().toString()));
        } catch (Exception e) {
            fail(e.toString());
            e.printStackTrace();
        }
    }

    public void testForParse() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.FOR1);
            Assert.assertTrue("for".equalsIgnoreCase(context.getLexem(11).getValue()));
            Assert.assertTrue(context.getLexem(11).getLexemType() == Lexem.LEXEM_KEYWORD);
            Assert.assertTrue("to".equalsIgnoreCase(context.getLexem(15).getValue()));
            Assert.assertTrue(context.getLexem(15).getLexemType() == Lexem.LEXEM_KEYWORD);
            Assert.assertTrue("do".equalsIgnoreCase(context.getLexem(17).getValue()));
            Assert.assertTrue(context.getLexem(17).getLexemType() == Lexem.LEXEM_KEYWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void testWhileParse() {
        try {
            ILexem[] lexems = new PascalParser().parse(PascalPrograms.WHILE1);
            assertTrue(lexems[7] instanceof IKeyWordLexem);
        }
        catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    public void testRepeat() {
        try {
            ILexem[] lexems = new PascalParser().parse(PascalPrograms.REPEAT1);
            assertTrue(lexems[7].getLexemType() == Lexem.LEXEM_KEYWORD);
            assertTrue(lexems[14].getLexemType() == Lexem.LEXEM_KEYWORD);
        }
        catch (Exception e) {
            fail(e.toString());
            e.printStackTrace();
        }
    }

    public void testArray() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.ARRAY1);
            context.movePointer(4);
            assertTrue(context.getCurLexem() instanceof ArrayLexem);
            context.incrementPointer();
            assertTrue(context.getCurLexem() instanceof OperatorLexem);
            context.movePointer(2);
            assertTrue(context.getCurLexem() instanceof OperatorLexem);
            assertTrue("..".equalsIgnoreCase(context.getCurLexem().getValue()));
            context.incrementPointer();
            assertTrue(context.getCurLexem() instanceof IConstant);
            context.movePointer(2);
            assertTrue(context.getLexem(18) instanceof OfLexem);
        } catch (Exception e) {
            fail(e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void runTest() {
        testIfParse();
        testParseBooleanExpression();
        testForParse();
        testDoAssign();
        testWhileParse();
        testRepeat();
        testArray();
    }
}
