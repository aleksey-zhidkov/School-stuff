package ru.lexx.acsystem.test.backend.task;

import junit.framework.TestCase;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.IntegerRowHandler;
import ru.jdev.utils.db.rowhandlers.StringRowHandler;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.CheckedTasksManager;
import ru.lexx.acsystem.backend.task.GivenTasksManager;
import ru.lexx.acsystem.backend.task.Task;
import ru.lexx.acsystem.backend.user.SystemGroupsManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 23:07:10
 */
public class TaskOperationsTest extends TestCase {

    private static final String SELECT_GROUP = "SELECT id FROM system_groups " +
                                               " WHERE prog_lang = 'Pascal'";

    private static final String SELECT_GTASK_COUNT = "SELECT count(*) FROM given_tasks";

    public TaskOperationsTest() {
    }

    public TaskOperationsTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testTaskOperations() {
        try {
            String pr = (String) ConnectionManager.executeQuery(SELECT_GROUP,
                                                                new Object[0],
                                                                new StringRowHandler("id"))[0];
            UserAccaunt a = UserManager.createAccaunt("user_with_very_lon", "pass", "fio", "email", SystemGroupsManager.DEFAULT_GROUP);
            int init_gt = ((Integer) ConnectionManager.executeQuery(SELECT_GTASK_COUNT,
                                                                    new Object[]{},
                                                                    new IntegerRowHandler("count(*)"))[0]).intValue();
            assertNull(GivenTasksManager.getUserTask(a));
            GivenTasksManager.giveTask("1", a);
            int after_get_gt = ((Integer) ConnectionManager.executeQuery(SELECT_GTASK_COUNT,
                                                                         new Object[]{},
                                                                         new IntegerRowHandler("count(*)"))[0]).intValue();
            assertTrue(init_gt + 1 == after_get_gt);

            Task t = GivenTasksManager.getUserTask(a);
            assertNotNull(t);
            assertTrue("1".equalsIgnoreCase(GivenTasksManager.getUserTask(a).getId() + ""));
            CheckedTasksManager.returnTask(a, t, 5);
            int after_ret_gt = ((Integer) ConnectionManager.executeQuery(SELECT_GTASK_COUNT,
                                                                         new Object[]{},
                                                                         new IntegerRowHandler("count(*)"))[0]).intValue();
            assertTrue(init_gt + 1 == after_ret_gt);
            assertNull(GivenTasksManager.getUserTask(a));
            UserManager.deleteAccaunt(a.getId());
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(getName() + ".", e);
        }
    }

    @Override
    public void runTest() {
        testTaskOperations();
    }
}
