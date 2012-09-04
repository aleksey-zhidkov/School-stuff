package ru.lexx.acsystem.test.intepretator.pascal;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;
import ru.lexx.acsystem.test.intepretator.pascal.function.FunctionSuite;
import ru.lexx.acsystem.test.intepretator.pascal.lexem.LexemSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 13:00:48
 * To change this template use File | Settings | File Templates.
 */
public class PascalSuite extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.intepretator.pascal";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(FunctionSuite.suite());
        suite.addTest(LexemSuite.suite());
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}