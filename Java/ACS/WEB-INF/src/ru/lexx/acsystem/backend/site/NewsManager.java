package ru.lexx.acsystem.backend.site;

import ru.jdev.common.ArrayUtils;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditor;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Pattern;

public class NewsManager implements ACSUIEditor {

    private static final RowHandler handler = new NewsRowHandler();

    private static final String SELECT_NEWS = "SELECT id,news_date,news_text " +
                                              "FROM news " +
                                              "ORDER BY news_date DESC";

    private static final String INSERT_NEWS_ITEM = "INSERT INTO news (id,news_text,news_date) " +
                                                   "VALUES (?,?,?)";

    private static final String UPDATE_NEWS_ITEM = "UPDATE news SET " +
                                                   "news_date = ?, " +
                                                   "news_text = ? " +
                                                   "WHERE id = ?";

    private static final String DELETE_NEWS_ITEM = "DELETE FROM news " +
                                                   "WHERE id = ?";

    private static final String SELECT_NEWS_ITEM = "SELECT id,news_date,news_text " +
                                                   "FROM news " +
                                                   "WHERE id = ?";

    private static List<NewsItem> items = new ArrayList<NewsItem>();

    static {
        load();
    }

    public static synchronized NewsItem createNews(String text, Date date) {
        Timestamp currentTime;
        if (date == null)
            currentTime = new Timestamp(System.currentTimeMillis());
        else
            currentTime = new Timestamp(date.getTime());
        NewsItem ni;
        try {
            int hook = SystemHookGenerator.getNextHook();
            ni = new NewsItem(new Timestamp(currentTime.getTime()), text, hook);
            items.add(0, ni);
            Collections.sort(items);
            ConnectionManager.executeUpadte(INSERT_NEWS_ITEM, new Object[]{hook, text, currentTime});

        } catch (SQLException e) {
            SystemManager.getLogger().log(NewsManager.class.getName() + ".createNews", e);
            return null;
        } catch (Exception e) {
            SystemManager.getLogger().log(NewsManager.class.getName() + ".createNews", e);
            return null;
        }
        return ni;
    }

    public static synchronized NewsItem[] getLastNews(int count) {
        if (count > items.size())
            count = items.size();
        NewsItem[] nws = new NewsItem[count];
        for (int i = 0; i < count; i++)
            nws[i] = items.get(i);
        return nws;
    }

    public static synchronized int updateNews(String text, Date date, String id) {
        try {
            int hook = Integer.parseInt(id);
            int i = 0;
            for (; i < items.size(); i++) {
                if (items.get(i).getHook() == hook)
                    break;
            }
            items.set(i, new NewsItem(new Timestamp(date.getTime()), text, hook));
            Collections.sort(items);
            return ConnectionManager.executeUpadte(UPDATE_NEWS_ITEM,
                                                   new Object[]{new java.sql.Date(date.getTime()), text, id});
        } catch (SQLException e) {
            SystemManager.getLogger().log(NewsManager.class.getName() + ".updateNews", e);
            return 0;
        }
    }

    public static synchronized int deleteNews(int hook) {
        try {
            int res = ConnectionManager.executeUpadte(DELETE_NEWS_ITEM, new Object[]{hook});
            int i;
            for (i = 0; i < items.size(); i++) {
                if (items.get(i).getHook() == hook)
                    break;
            }
            items.remove(i);
            return res;
        } catch (SQLException e) {
            SystemManager.getLogger().log(NewsManager.class.getName() + ".deleteNews", e);
            return 0;
        }
    }

    public static synchronized void load() {
        Object[] res;
        try {
            res = ConnectionManager.executeQuery(SELECT_NEWS,
                                                 ACSConstants.ZERO_ARRAY,
                                                 handler);
        } catch (SQLException e) {
            SystemManager.getLogger().log(NewsManager.class.getName() + ".static", e);
            res = new Object[0];
        }
        items.clear();
        ArrayUtils.putArrayToList(res, items);
    }

    public static synchronized void reload() {
        load();
    }

    public ACSUIEditable getEditable(SimpleRequestContext src) {
        return getNews(src.getInt("id"));
    }

    private NewsItem getNews(int id) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_NEWS_ITEM, new Object[]{new Integer(id)}, handler);
            return (NewsItem) res[0];
        } catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".getNews", e);
        }
        return null;
    }

    public ACSUIEditable getEditable() {
        return new NewsItem(null, "", -1);
    }

    public ACSUIEditable updatetEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1]) {
            Calendar cal = Calendar.getInstance();
            String d = src.getString("date");
            int p1 = d.indexOf(".");
            int p2 = d.lastIndexOf(".");
            cal.set(Calendar.YEAR, Integer.parseInt(d.substring(p2 + 1)));
            cal.set(Calendar.MONTH, Integer.parseInt(d.substring(p1 + 1, p2)) - 1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d.substring(0, p1)));
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            updateNews(src.getString("text"), cal.getTime(), src.getString("id"));
            src.setObject("news", NewsManager.getLastNews(5));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Новость обновлена", StatusMessageType.OK_MESSAGE));
            return getNews(src.getInt("id"));
        } else {
            if (!cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Нет содержания", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректная дата", StatusMessageType.ERROR_MESSAGE));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Новость не обновлена", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public ACSUIEditable insertEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1]) {
            Calendar cal = Calendar.getInstance();
            String d = src.getString("date");
            int p1 = d.indexOf(".");
            int p2 = d.lastIndexOf(".");
            cal.set(Calendar.YEAR, Integer.parseInt(d.substring(p2 + 1)));
            cal.set(Calendar.MONTH, Integer.parseInt(d.substring(p1 + 1, p2)) - 1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(d.substring(0, p1)));
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            src.setObject("news", NewsManager.getLastNews(5));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Новость добавлена", StatusMessageType.OK_MESSAGE));
            return createNews(src.getString("text"), cal.getTime());
        } else {
            if (!cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Нет содержания", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректная дата", StatusMessageType.ERROR_MESSAGE));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Новость не обновлена", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public ACSUIEditable deleteEditable(SimpleRequestContext src) {
        NewsItem ni = getNews(src.getInt("id"));
        int res = deleteNews(src.getInt("id"));
        if (res == 0) {
            ni = null;
            ((ACSRequestContext) src).addMessage(new  StatusMessage("Новость не удалена", StatusMessageType.ERROR_MESSAGE));
        } else
            ((ACSRequestContext) src).addMessage(new  StatusMessage("Новость удалена", StatusMessageType.OK_MESSAGE));
        src.setObject("news", NewsManager.getLastNews(5));
        return ni;
    }

    public ACSUIEditable[] getEditables(SimpleRequestContext src) {
        return getAllNews();
    }

    private NewsItem[] getAllNews() {
        return getLastNews(Integer.MAX_VALUE);
    }

    public String[] getColNames() {
        return new String[]{"Ид", "Дата", "Содержание"};
    }

    public String getType() {
        return "news";
    }

    public boolean[] checkInputData(ACSRequestContext ctx) {
        boolean[] res = new boolean[2];
        res[0] = ctx.getString("text").length() > 0;
        res[1] = Pattern.matches("^\\d{2}\\.\\d{2}\\.\\d{2}$", ctx.getString("date"));
        return res;
    }

    public String getManagmentLabel() {
        return "Управление новостями";
    }

    public String getAddLabel() {
        return "Добавление новости";
    }

    public String getUpdateLabel() {
        return "Обновление новости";
    }
}
