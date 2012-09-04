package ru.lexx.acsystem.backend.user;

import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.system.SystemManager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 14:11:43
 */
public class UserRowHandler implements RowHandler {
    public Object processRow(ResultSet rs) {
        try {
            return new UserAccaunt(rs.getString("login"),
                                   rs.getString("fio"),
                                   rs.getString("email"),
                                   new SystemGroup(RightsManager.buildRights(rs.getString("security")),
                                                   rs.getString("group_name"),
                                                   ProgLanguage.getByName(rs.getString("prog_lang")), rs.getInt("sys_group")),
                                   0, rs.getInt("u.id"), rs.getDate("reg_date"));
        } catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".processRow", e);
            return null;
        }
    }
}
