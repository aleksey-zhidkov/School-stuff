package ru.lexx.acsystem.backend.messenger;

import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: malinka
 * Date: 31.01.2006
 * Time: 20:50:56
 * To change this template use File | Settings | File Templates.
 */
public class MessageRowHandler implements RowHandler {
    public Object processRow(ResultSet rs) throws SQLException {

        return new Message(rs.getString("from_user"), rs.getString("to_user"), rs.getString("message_body"),
                           rs.getTimestamp("sent_date"), rs.getTimestamp("view_date"), rs.getTimestamp("read_date"), rs.getString("folder"),
                           rs.getInt("id"));
    }
}
