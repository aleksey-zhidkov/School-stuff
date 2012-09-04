package ru.lexx.acsystem.test.backend.events;

import junit.framework.TestCase;
import ru.jdev.utils.db.ConnectionManager;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.constants.CommonsHandlers;
import ru.lexx.acsystem.backend.events.EventManager;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.backend.user.SystemGroupsManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 04.03.2006
 * Time: 21:05:37
 */
public class AddEventTest extends TestCase {

    private UserAccaunt accaunt;

    public AddEventTest() {
    }

    public AddEventTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void setUp() {
        accaunt = UserManager.createAccaunt("AddEventTest.test_accaunt", "", "", "", SystemGroupsManager.DEFAULT_GROUP);
    }

    public void runTest() {
        try {
            int bc = (Integer) ConnectionManager.executeQuery("SELECT count(*) FROM events", ACSConstants.ZERO_ARRAY, CommonsHandlers.INTEGER_ROW_HANDLER_COUNT)[0];
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", accaunt.getLogin());
            EventManager.addEvent("events/user_registered", params, accaunt.getId(), new Date());
            int ac = (Integer) ConnectionManager.executeQuery("SELECT count(*) FROM events", ACSConstants.ZERO_ARRAY, CommonsHandlers.INTEGER_ROW_HANDLER_COUNT)[0];
            assertEquals(ac, bc + 1);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void tearDown() {
        UserManager.deleteAccaunt(accaunt.getId());
    }
}
