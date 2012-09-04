package ru.lexx.acsystem.test.webinterface;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;
import ru.lexx.acsystem.test.webinterface.backend.BackendTest;
import ru.lexx.acsystem.test.webinterface.phandlers.PagesTest;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 14.10.2005
 * Time: 15:07:59
 */
public class WebinterfaceTest extends GenericTestSuite {

    private static final String CLASS_PATH = "ru.lexx.acsystem.test.webinterface";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(PagesTest.suite());
        suite.addTest(BackendTest.suite());
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}
