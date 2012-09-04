package ru.lexx.acsystem.backend.task.stat;

import ru.jdev.utils.db.rowhandlers.RowHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 16.03.2006
 * Time: 1:23:07
 */
public class GStatUnitRowHandler implements RowHandler {
    public Object processRow(ResultSet rs) throws SQLException {
        return new GStatUnit(rs.getInt("id"), rs.getInt("task_id"), rs.getInt("user_id"), rs.getDate("give_date"),
                             rs.getDate("check_date"), rs.getFloat("points"), rs.getString("tp.name"));
    }
}
