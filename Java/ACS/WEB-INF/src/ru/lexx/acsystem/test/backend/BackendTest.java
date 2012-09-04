package ru.lexx.acsystem.test.backend;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;
import ru.lexx.acsystem.test.backend.events.EventsSuite;
import ru.lexx.acsystem.test.backend.site.SiteSuite;
import ru.lexx.acsystem.test.backend.system.SystemTest;
import ru.lexx.acsystem.test.backend.task.TaskSuite;
import ru.lexx.acsystem.test.backend.user.UserSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 26.10.2005
 * Time: 17:21:49
 * To change this template use File | Settings | File Templates.
 */
public class BackendTest extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.backend";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(SystemTest.suite());
        suite.addTest(TaskSuite.suite());
        suite.addTest(UserSuite.suite());
        suite.addTest(SiteSuite.suite());
        suite.addTest(EventsSuite.suite());
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}