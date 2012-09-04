package ru.lexx.acsystem.backend.site;


import ru.lexx.acsystem.backend.user.RightsType;

import java.util.Comparator;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 16:58:10
 */
public class MenuItemComparator implements Comparator {

    /**
     * Method for comparing <code>MenuItem</code> objects. Comments:
     * <ul>
     * <li> In first comparing rigths.
     * <li> If security are equals, then compared orders.
     * </ul>
     *
     * @param o1 First menu item
     * @param o2 Second menu item
     * @return -1 if o1 must be before o2, 1 if o1 must be after o2 and else 0.
     */
    public int compare(Object o1, Object o2) {
        MenuItem i1 = (MenuItem) o1;
        MenuItem i2 = (MenuItem) o2;
        int res;
        if ((res = RightsType.compareRights(i1.getRights(), i2.getRights())) != 0)
            return res;

        if (i1.getOrder() < 0)
            return -1;
        else if (i2.getOrder() < 0)
            return 1;
        else if (i1.getOrder() < i2.getOrder())
            return -1;
        else if (i1.getOrder() > i2.getOrder())
            return 1;
        else
            return 0;
    }
}
