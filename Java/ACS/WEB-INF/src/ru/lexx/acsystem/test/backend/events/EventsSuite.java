package ru.lexx.acsystem.test.backend.events;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 04.03.2006
 * Time: 21:15:49
 */
public class EventsSuite extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.backend.events";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}
