package ru.lexx.acsystem.backend.site;

import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.backend.system.SystemManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NewsRowHandler implements RowHandler {

    public Object processRow(ResultSet rs) {
        try {
            return new NewsItem(rs.getTimestamp("news_date"),
                                rs.getString("news_text"),
                                rs.getInt("id"));
        } catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".processRow", e);
            return null;
        }
    }
}
