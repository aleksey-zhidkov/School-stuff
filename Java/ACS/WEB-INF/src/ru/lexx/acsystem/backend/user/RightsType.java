package ru.lexx.acsystem.backend.user;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 19.02.2006
 * Time: 19:45:07
 */
public enum RightsType {
    RIGHTS_NONE,
    RIGHTS_REGISTERED,
    RIGHTS_USER,
    RIGHTS_ADMIN;

    public static final int RIGHTS_NONE_INT = 1000;
    public static final int RIGHTS_REGISTERED_INT = 2000;
    public static final int RIGHTS_USER_INT = 3000;
    public static final int RIGHTS_ADMIN_INT = 4000;

    private static Map<RightsType, Integer> intResp = new HashMap<RightsType, Integer>();

    static {
        intResp.put(RIGHTS_NONE, new Integer(RIGHTS_NONE_INT));
        intResp.put(RIGHTS_REGISTERED, new Integer(RIGHTS_REGISTERED_INT));
        intResp.put(RIGHTS_USER, new Integer(RIGHTS_USER_INT));
        intResp.put(RIGHTS_ADMIN, new Integer(RIGHTS_ADMIN_INT));
    }

    public static int compareRights(RightsType rights, RightsType rights1) {
        return intResp.get(rights).compareTo(intResp.get(rights1));
    }
}
