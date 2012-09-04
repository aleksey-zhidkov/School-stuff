package ru.lexx.acsystem.test.webinterface.phandlers;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 14.10.2005
 * Time: 16:34:29
 * To change this template use File | Settings | File Templates.
 */
public final class PagesTest extends GenericTestSuite {
    protected final static String CLASS_PATH = "ru.lexx.acsystem.test.webinterface.phandlers";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}
