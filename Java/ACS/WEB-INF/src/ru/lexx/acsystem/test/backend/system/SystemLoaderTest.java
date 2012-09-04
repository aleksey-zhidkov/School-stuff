package ru.lexx.acsystem.test.backend.system;

import junit.framework.Assert;
import junit.framework.TestCase;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.StringRowHandler;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.backend.system.SystemManager;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 26.10.2005
 * Time: 17:23:32
 * To change this template use File | Settings | File Templates.
 */
public class SystemLoaderTest extends TestCase {

    private static final String TEST_SQL = "SELECT * " +
                                           "FROM system_groups";

    public SystemLoaderTest(String s) {
        super(s);
    }

    public void testLoader() {
        try {
            SystemLoader l = new SystemLoader();
            ConnectionManager.executeQuery(TEST_SQL,
                                           new Object[]{},
                                           new StringRowHandler("id"));
            Assert.assertNotNull(SystemManager.getLogger());
            Assert.assertNotNull(SystemManager.getProperty("LOG_DIR"));
            Assert.assertNotNull(SystemManager.getText("delete"));
        }
        catch (Exception e) {
            fail(e.toString());
        }
    }

    @Override
    public void runTest() {
        testLoader();
    }
}
