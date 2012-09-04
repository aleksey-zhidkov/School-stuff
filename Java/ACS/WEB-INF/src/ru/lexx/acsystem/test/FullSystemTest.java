package ru.lexx.acsystem.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import ru.lexx.acsystem.test.backend.BackendTest;
import ru.lexx.acsystem.test.intepretator.InterpretaterSuite;
import ru.lexx.acsystem.test.webinterface.WebinterfaceTest;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 14.10.2005
 * Time: 14:48:24
 */
public class FullSystemTest extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(WebinterfaceTest.suite());
        suite.addTest(BackendTest.suite());
        suite.addTest(InterpretaterSuite.suite());
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }

    public static void main(String[] args) {
        new TestRunner().doRun(suite());
    }
}
