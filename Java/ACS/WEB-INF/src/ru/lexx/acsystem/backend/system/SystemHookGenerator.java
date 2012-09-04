package ru.lexx.acsystem.backend.system;

import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.StringRowHandler;
import ru.lexx.acsystem.backend.constants.ACSConstants;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 18.11.2005
 * Time: 0:28:59
 */
public class SystemHookGenerator {

    private static int currentHook;

    private static final String LOAD_SQL = "SELECT current_hook FROM system_hook";
    private static final String SAVE_SQL = "UPDATE system_hook SET current_hook = ?";

    public static synchronized void initialize() throws Exception {
        currentHook = Integer.parseInt((String) ConnectionManager.executeQuery(LOAD_SQL,
                                                                               ACSConstants.ZERO_ARRAY,
                                                                               new StringRowHandler("current_hook"))[0]);
    }

    public static synchronized int getNextHook() throws Exception {
        ConnectionManager.executeUpadte(SAVE_SQL, new Object[]{new Integer(currentHook + 1)});
        return currentHook++;
    }
}
