package ru.lexx.acsystem.test.backend.task;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 13:14:59
 */
public class TaskSuite extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.backend.task";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}