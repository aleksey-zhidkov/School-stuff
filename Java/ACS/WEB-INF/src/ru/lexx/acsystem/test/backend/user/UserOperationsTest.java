package ru.lexx.acsystem.test.backend.user;

import junit.framework.TestCase;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.IntegerRowHandler;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.backend.user.RightsType;
import ru.lexx.acsystem.backend.user.SystemGroupsManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 13:44:46
 */
public class UserOperationsTest extends TestCase {

    private static final String SELECT_USERS_COUNT = "SELECT count(*) FROM users";

    public UserOperationsTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testTaskOperation() {
        try {
            int users = ((Integer) ConnectionManager.executeQuery(SELECT_USERS_COUNT,
                                                                  new Object[]{},
                                                                  new IntegerRowHandler("count(*)"))[0]).intValue();
            UserManager.createAccaunt("testing_user_with_u", "pass", "‘»Œ", "email", SystemGroupsManager.DEFAULT_GROUP);
            int u2 = ((Integer) ConnectionManager.executeQuery(SELECT_USERS_COUNT,
                                                               new Object[]{},
                                                               new IntegerRowHandler("count(*)"))[0]).intValue();
            assertTrue(users + 1 == u2);
            UserAccaunt a = UserManager.getAccaunt("testing_user_with_u", "pass");
            assertTrue(a.getSys_group().getRights().contains(RightsType.RIGHTS_USER));
            assertTrue("email".equalsIgnoreCase(a.getEmail()));
            assertTrue("‘»Œ".equalsIgnoreCase(a.getFio()));
            assertTrue(a.getSys_group().getLang() == ProgLanguage.NONE);
            UserManager.deleteAccaunt(a.getId());
            int u3 = ((Integer) ConnectionManager.executeQuery(SELECT_USERS_COUNT,
                                                               new Object[]{},
                                                               new IntegerRowHandler("count(*)"))[0]).intValue();
            assertTrue(users == u3);
        } catch (SQLException e) {
            fail(e.toString());
            e.printStackTrace();
        }
    }

    public void runTest() {
        testTaskOperation();
    }
}
