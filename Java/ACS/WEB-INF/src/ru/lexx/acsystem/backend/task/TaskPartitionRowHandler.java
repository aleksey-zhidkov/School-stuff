package ru.lexx.acsystem.backend.task;

import ru.jdev.utils.db.rowhandlers.RowHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 24.02.2006
 * Time: 12:03:08
 */
public class TaskPartitionRowHandler implements RowHandler {
    public Object processRow(ResultSet rs) throws SQLException {
        return new TaskPartition(rs.getInt("id"), rs.getString("name"), rs.getInt("need_to_resolve"),
                                 rs.getFloat("need_avg_points"), rs.getInt("part_order"));
    }
}
