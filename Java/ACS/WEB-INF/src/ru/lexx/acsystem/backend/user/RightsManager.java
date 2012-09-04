package ru.lexx.acsystem.backend.user;

import ru.lexx.acsystem.backend.system.SystemManager;
import ru.jdev.html.forms.elements.SelectElement;

import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 19.02.2006
 * Time: 19:50:22
 */
public class RightsManager {

    public static Rights buildRights(String rightsStr) {
        try {
            StringTokenizer st = new StringTokenizer(rightsStr, ",");
            Rights r;
            if (st.hasMoreTokens())
                r = new Rights(RightsType.valueOf(st.nextToken()));
            else
                return new Rights(RightsType.RIGHTS_NONE);
            while (st.hasMoreTokens()) {
                try {
                    r.add(RightsType.valueOf(st.nextToken()));
                } catch (Exception e) {
                    SystemManager.getLogger().log(RightsManager.class.getName() + ".buildRights", e);
                }                
            }
            return r;
        } catch (Exception e) {
            SystemManager.getLogger().log(RightsManager.class.getName() + ".buildRights", e);
            return new Rights(RightsType.RIGHTS_NONE);
        }
    }

    public static SelectElement.Option[] getOptinos() {
        RightsType[] rts = RightsType.values();
        SelectElement.Option[] ops = new SelectElement.Option[rts.length];
        for (int i = 0; i < rts.length; i++) {
            ops[i] = new SelectElement.Option(rts[i].toString(), rts[i].toString());
        }
        return ops;
    }

    public static String rightToString(Rights rights) {
        StringBuffer res = new StringBuffer();
        RightsType[] rs = rights.getAllFollows();
        for (RightsType r : rs) {
            res.append(r.toString());
            res.append(",");
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }
}
