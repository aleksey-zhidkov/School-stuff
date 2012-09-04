package ru.lexx.acsystem.test.intepretator.common.backend;

import junit.framework.Test;
import junit.framework.TestSuite;
import ru.lexx.acsystem.test.GenericTestSuite;
import ru.lexx.acsystem.test.intepretator.common.backend.lexem.LexemSuite;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 06.11.2005
 * Time: 14:31:44
 */
public class BackendSuite extends GenericTestSuite {
    private static final String CLASS_PATH = "ru.lexx.acsystem.test.intepretator.goods.backend";

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTest(LexemSuite.suite());
        for (Test t : findClasses(CLASS_PATH))
            suite.addTest(t);
        return suite;
    }
}