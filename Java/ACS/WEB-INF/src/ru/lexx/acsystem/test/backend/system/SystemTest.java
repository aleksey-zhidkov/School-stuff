package ru.lexx.acsystem.test.backend.system;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 26.10.2005
 * Time: 17:21:13
 * To change this template use File | Settings | File Templates.
 */
public class SystemTest extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.backend.system";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}