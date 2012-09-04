package ru.lexx.acsystem.backend.site;

import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.user.RightsType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 15:45:45
 */
public class MenuRowHandler implements RowHandler {

    public Object processRow(ResultSet rs) {
        try {
            return new MenuItem(rs.getString("name"),
                                rs.getString("url"),
                                rs.getInt("menu_order"),
                                RightsType.valueOf(rs.getString("security")),
                                rs.getInt("id"));
        } catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".processRow", e);
            return null;
        }
    }

}
