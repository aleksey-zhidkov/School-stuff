package ru.lexx.acsystem.test.webinterface.backend;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 15.10.2005
 * Time: 22:07:19
 * To change this template use File | Settings | File Templates.
 */
public class BackendTest extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.webinterface.backend";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}
