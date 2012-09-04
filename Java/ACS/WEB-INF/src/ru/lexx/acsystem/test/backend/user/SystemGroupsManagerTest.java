package ru.lexx.acsystem.test.backend.user;

import junit.framework.TestCase;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemLoader;
import ru.lexx.acsystem.backend.user.RightsType;
import ru.lexx.acsystem.backend.user.SystemGroupsManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 23:23:11
 */
public class SystemGroupsManagerTest extends TestCase {
    public SystemGroupsManagerTest() {
    }

    public SystemGroupsManagerTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testSelectPL() {
        UserAccaunt a = UserManager.createAccaunt("user_with_very_long", "pass", "fio", "emial", SystemGroupsManager.DEFAULT_GROUP);
        assertTrue(a.getSys_group().getLang() == ProgLanguage.NONE);
        assertTrue(a.getSys_group().getRights().contains(RightsType.RIGHTS_USER));
        UserManager.deleteAccaunt(a.getId());
    }

    public void runTest() {
        testSelectPL();
    }
}
