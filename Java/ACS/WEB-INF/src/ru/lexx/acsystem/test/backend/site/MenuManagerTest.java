package ru.lexx.acsystem.test.backend.site;

import junit.framework.TestCase;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.IntegerRowHandler;
import ru.lexx.acsystem.backend.site.MenuItem;
import ru.lexx.acsystem.backend.site.MenuManager;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.backend.user.Rights;
import ru.lexx.acsystem.backend.user.RightsType;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 23:43:00
 */
public class MenuManagerTest extends TestCase {

    private static final String SELECT_ADMIN_MENU_COUNT = "SELECT count(*) FROM links " +
                                                          "WHERE security = 'RIGHTS_ADMIN'";

    private static final String SELECT_NONE_MENU_COUNT = "SELECT count(*) FROM links " +
                                                         "WHERE security = 'RIGHTS_NONE'";

    public MenuManagerTest() {
    }

    public MenuManagerTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testHaveAccess() {
        assertTrue(MenuManager.haveAccess(new Rights(RightsType.RIGHTS_NONE), "sorry"));
        assertTrue(MenuManager.haveAccess(new Rights(RightsType.RIGHTS_USER), "sorry"));
        assertTrue(MenuManager.haveAccess(new Rights(RightsType.RIGHTS_ADMIN), "sorry"));

        assertFalse(MenuManager.haveAccess(new Rights(RightsType.RIGHTS_NONE), "gettask"));
        assertFalse(MenuManager.haveAccess(new Rights(RightsType.RIGHTS_ADMIN), "gettask"));
        assertTrue(MenuManager.haveAccess(new Rights(RightsType.RIGHTS_USER), "gettask"));

        assertFalse(MenuManager.haveAccess(new Rights(RightsType.RIGHTS_NONE), "viewmessages"));
        assertTrue(MenuManager.haveAccess(new Rights(RightsType.RIGHTS_ADMIN), "viewmessages"));
        assertFalse(MenuManager.haveAccess(new Rights(RightsType.RIGHTS_USER), "viewmessages"));
    }

    public void testMenuOps() {
        try {
            // init admins menu count from manager
            int iamcm = MenuManager.getMenu(RightsType.RIGHTS_ADMIN).length;
            // init admins menu count from DB
            int iamcdb = ((Integer) ConnectionManager.executeQuery(SELECT_ADMIN_MENU_COUNT,
                                                                   new Object[]{},
                                                                   new IntegerRowHandler("count(*)"))[0]).intValue();
            // init none menu count from manager
            int inmcm = MenuManager.getMenu(RightsType.RIGHTS_NONE).length;
            // none menu count from DB after updating a item
            int inmcdb = ((Integer) ConnectionManager.executeQuery(SELECT_NONE_MENU_COUNT,
                                                                   new Object[]{},
                                                                   new IntegerRowHandler("count(*)"))[0]).intValue();

            MenuItem i = MenuManager.createMenuItem("menu_with_very_unique_name", "url", 100, RightsType.RIGHTS_ADMIN);
            MenuItem[] mns = MenuManager.getMenu(RightsType.RIGHTS_ADMIN);
            assertTrue("menu_with_very_unique_name".equalsIgnoreCase(mns[mns.length - 1].getName()));
            // admins menu count from manager after adding a item
            int aaamcm = MenuManager.getMenu(RightsType.RIGHTS_ADMIN).length;
            // admins menu count from DB after adding a item
            int aaamcdb = ((Integer) ConnectionManager.executeQuery(SELECT_ADMIN_MENU_COUNT,
                                                                    new Object[]{},
                                                                    new IntegerRowHandler("count(*)"))[0]).intValue();
            assertTrue(iamcm + 1 == aaamcm);
            assertTrue(iamcdb + 1 == aaamcdb);
            MenuManager.updateMenuItem(i.getId(),
                                       "new_menu_with_very_unique_name",
                                       "url222",
                                       "RIGHTS_NONE",
                                       101);
            // admins menu count from manager after updating a item
            int aaumcm = MenuManager.getMenu(RightsType.RIGHTS_ADMIN).length;
            // admins menu count from DB after updating a item
            int aaumcdb = ((Integer) ConnectionManager.executeQuery(SELECT_ADMIN_MENU_COUNT,
                                                                    new Object[]{},
                                                                    new IntegerRowHandler("count(*)"))[0]).intValue();
            assertTrue(iamcm == aaumcm);
            assertTrue(iamcdb == aaumcdb);
            assertFalse("menu_with_very_unique_name".equalsIgnoreCase(mns[mns.length - 1].getName()));

            // none menu count from manager after updating a item
            int naumcm = MenuManager.getMenu(RightsType.RIGHTS_NONE).length;
            // none menu count from DB after updating a item
            int naumcdb = ((Integer) ConnectionManager.executeQuery(SELECT_NONE_MENU_COUNT,
                                                                    new Object[]{},
                                                                    new IntegerRowHandler("count(*)"))[0]).intValue();
            assertTrue(inmcdb + 1 == naumcdb);
            assertTrue(inmcm + 1 == naumcm);
            mns = MenuManager.getMenu(RightsType.RIGHTS_NONE);
            MenuItem item = mns[mns.length - 1];
            assertTrue("new_menu_with_very_unique_name".equalsIgnoreCase(item.getName()));
            assertTrue("url222".equalsIgnoreCase(item.getUrl()));
            assertTrue(item.getOrder() == 101);
            assertTrue(item.getRights() == RightsType.RIGHTS_NONE);

            MenuManager.removeMenuItem("new_menu_with_very_unique_name");

            // none menu count from manager after removing a item
            int narmcm = MenuManager.getMenu(RightsType.RIGHTS_NONE).length;
            // none menu count from DB after remobing a item
            int narmcdb = ((Integer) ConnectionManager.executeQuery(SELECT_NONE_MENU_COUNT,
                                                                    new Object[]{},
                                                                    new IntegerRowHandler("count(*)"))[0]).intValue();
            assertTrue(inmcdb == narmcdb);
            assertTrue(inmcm == narmcm);
            MenuManager.toStringS();
        } catch (SQLException e) {
            fail(e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void runTest() {
        testHaveAccess();
        testMenuOps();
    }
}
