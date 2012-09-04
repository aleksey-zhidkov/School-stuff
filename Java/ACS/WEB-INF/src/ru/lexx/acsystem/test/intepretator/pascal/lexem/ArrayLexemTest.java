package ru.lexx.acsystem.test.intepretator.pascal.lexem;

import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.interpretator.common.ProgramContext;
import ru.lexx.acsystem.interpretator.common.lexem.IKeyWordLexem;
import ru.lexx.acsystem.interpretator.common.variable.ArrayVariable;
import ru.lexx.acsystem.test.envoirment.PascalPrograms;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 07.11.2005
 * Time: 16:01:19
 */
public class ArrayLexemTest extends TestCase {
    public ArrayLexemTest() {
    }

    public ArrayLexemTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testArray() {
        try {
            ProgramContext context = new ProgramContext(ProgLanguage.PASCAL, PascalPrograms.ARRAY1);
            context.movePointer(4);
            ((IKeyWordLexem) context.getCurLexem()).changeState(context);
            assertTrue((ArrayVariable) context.getVariable("arr1") instanceof ArrayVariable);
            ArrayVariable var = (ArrayVariable) context.getVariable("arr1" +
                                                                    "");
            assertTrue(var.size() == 5);
            assertTrue(((ArrayVariable) var.get(1)).size() == 4);
            assertTrue(((ArrayVariable) ((ArrayVariable) var.get(1)).get(3)).size() == 5);
            assertTrue("0".equalsIgnoreCase(((ArrayVariable) ((ArrayVariable) var.get(1)).get(3)).get(2).getValue().toString()));
        } catch (Exception e) {
            fail(e.toString());
            e.printStackTrace();
        }
    }

    public void runTest() {
        testArray();
    }
}
