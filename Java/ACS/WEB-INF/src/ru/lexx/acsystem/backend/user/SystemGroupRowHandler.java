package ru.lexx.acsystem.backend.user;

import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.backend.constants.ProgLanguage;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 19.02.2006
 * Time: 23:13:45
 */
public class SystemGroupRowHandler implements RowHandler {
    public Object processRow(ResultSet rs) throws SQLException {
        return new SystemGroup(RightsManager.buildRights(rs.getString("security")), rs.getString("name"),
                               ProgLanguage.getByName(rs.getString("prog_lang")), rs.getInt("id"));
    }
}
