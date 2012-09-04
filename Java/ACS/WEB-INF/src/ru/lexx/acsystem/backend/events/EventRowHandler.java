package ru.lexx.acsystem.backend.events;

import ru.jdev.utils.db.rowhandlers.RowHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 09.03.2006
 * Time: 2:46:47
 */
public class EventRowHandler implements RowHandler {
    public Object processRow(ResultSet rs) throws SQLException {
        return new Event(rs.getInt("id"), rs.getString("description"), rs.getInt("subject_id"), rs.getDate("date"));
    }
}
