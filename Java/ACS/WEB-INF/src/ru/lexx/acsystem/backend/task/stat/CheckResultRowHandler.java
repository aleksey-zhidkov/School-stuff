package ru.lexx.acsystem.backend.task.stat;

import ru.jdev.utils.db.rowhandlers.RowHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: pipsi
 * Date: 22.01.2006
 * Time: 19:09:40
 * To change this template use File | Settings | File Templates.
 */
public class CheckResultRowHandler implements RowHandler {
    public Object processRow(ResultSet rs) throws SQLException {
        return new CheckResult(rs.getString("task_id"), rs.getDate("check_date"), rs.getInt("points"));
    }
}
