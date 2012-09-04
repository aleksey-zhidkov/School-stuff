package ru.lexx.acsystem.test.intepretator;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;
import ru.lexx.acsystem.test.intepretator.common.CommonSuite;
import ru.lexx.acsystem.test.intepretator.pascal.PascalSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 30.10.2005
 * Time: 13:01:48
 * To change this template use File | Settings | File Templates.
 */
public class InterpretaterSuite extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.intepretator";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(PascalSuite.suite());
        suite.addTest(CommonSuite.suite());
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}