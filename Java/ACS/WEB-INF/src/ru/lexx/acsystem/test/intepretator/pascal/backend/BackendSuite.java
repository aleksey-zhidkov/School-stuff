package ru.lexx.acsystem.test.intepretator.pascal.backend;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;
import ru.lexx.acsystem.test.intepretator.pascal.backend.function.FunctionSuite;
import ru.lexx.acsystem.test.intepretator.pascal.backend.lexem.LexemSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 12:59:28
 * To change this template use File | Settings | File Templates.
 */
public class BackendSuite extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.intepretator.pascal.backend";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(LexemSuite.suite());
        suite.addTest(FunctionSuite.suite());
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}