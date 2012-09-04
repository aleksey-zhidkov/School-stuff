package ru.lexx.acsystem.test.intepretator.common;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;
import ru.lexx.acsystem.test.intepretator.common.lexem.LexemSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 04.11.2005
 * Time: 21:31:21
 */
public class CommonSuite extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.intepretator.goods";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(LexemSuite.suite());
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}