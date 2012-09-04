package ru.lexx.acsystem.test.intepretator.pascal.backend.function;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 15:29:18
 */
public class FunctionSuite extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.intepretator.pascal.backend.function";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}