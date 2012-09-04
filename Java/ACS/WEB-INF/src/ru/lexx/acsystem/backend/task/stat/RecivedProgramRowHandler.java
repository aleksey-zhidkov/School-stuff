package ru.lexx.acsystem.backend.task.stat;

import ru.jdev.utils.db.rowhandlers.RowHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RecivedProgramRowHandler implements RowHandler {
    public Object processRow(ResultSet rs) throws SQLException {
        return new RecivedProgram(rs.getInt("id"),rs.getInt("user_id"), rs.getDate("recive_date"),
                                  rs.getString("formulation"), rs.getString("program"),
                                  rs.getInt("isOk") == 1);
    }
}
