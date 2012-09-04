package ru.lexx.acsystem.backend.user;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 19.02.2006
 * Time: 19:46:02
 */
public class Rights {

    private Rights next;
    private RightsType rights;

    public Rights(RightsType _rights) {
        rights = _rights;
    }

    void add(RightsType _rights) {
        if (next == null)
            next = new Rights(_rights);
        else
            next.add(_rights);
    }

    public boolean contains(RightsType _rights) {
        return rights == _rights || ((next == null) ? false : next.contains(_rights));
    }

    public RightsType[] getAllFollows() {
        ArrayList<RightsType> frights = new ArrayList<RightsType>();
        Rights r = this;
        do {
            frights.add(r.rights);
            r = r.next;
        } while (r != null);
        RightsType[] res = new RightsType[frights.size()];
        frights.toArray(res);
        return res;
    }

    public String toString() {
        StringBuffer res = new StringBuffer();
        Rights r = this;
        do {
            res.append(r.rights.toString() + ",");
            r = r.next;
        } while (r != null);
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }
}
