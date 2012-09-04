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
import ru.lexx.acsystem.backend.user.Rights;
import ru.lexx.acsystem.backend.user.RightsType;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class MenuManager implements ACSUIEditor {

    private static final RowHandler handler = new MenuRowHandler();

    private static final String SELECT_MENU = "SELECT id,name,url,menu_order,security " +
                                              "FROM links";

    private static final String SELECT_MENU_ITEM = "SELECT id,name,url,menu_order,security " +
                                                   "FROM links " +
                                                   "WHERE name = ?";

    private static final String SELECT_MENU_ITEM_BY_ID = "SELECT id,name,url,menu_order,security " +
                                                         "FROM links " +
                                                         "WHERE id = ?";

    private static final String INSERT_MENU_ITEM = "INSERT INTO links " +
                                                   "(id,name,url,menu_order,security) " +
                                                   "VALUES (?,?,?,?,?)";

    private static final String DELETE_MENU_ITEM = "DELETE FROM links WHERE name = ?";

    private static final String DELETE_MENU_ITEM_BY_ID = "DELETE FROM links WHERE id = ?";

    private static final String UPDATE_MENU_ITEM = "UPDATE links SET " +
                                                   "name = ?, " +
                                                   "url = ?, " +
                                                   "menu_order = ?, " +
                                                   "security = ? " +
                                                   "WHERE id = ?";

    /**
     * Кэш линок меню
     */
    private static List<MenuItem> items = new ArrayList<MenuItem>();

    static {
        load();
    }

    /**
     * Проверка имеют ли доступ пользователи с правами <code>security</code> к странице
     * <code>page</code>
     *
     * @param rights Имеющиеся права
     * @param page   Запрошенная страница
     * @return <b>true</b> если пользователи с данными правами имеют доступ к данной
     *         странице. Иначе - <b>false</b>
     */
    public static synchronized boolean haveAccess(Rights rights, String page) {
        for (MenuItem mi : items) {
            if (mi.getUrl().indexOf(page) != -1 && rights.contains(mi.getRights()))
                return true;
        }
        return false;
    }

    public static synchronized MenuItem createMenuItem(String name, String url, int order, RightsType rights) {
        int res;
        int hook = 0;
        try {
            hook = SystemHookGenerator.getNextHook();
            if (name.length() == 0)
                name = null;
            res = ConnectionManager.executeUpadte(INSERT_MENU_ITEM,
                                                  new Object[]{new Long(hook), name, url, order, rights.toString()});
        } catch (SQLException e) {
            SystemManager.getLogger().log(MenuManager.class.getName() + ".createMenuItem", e);
            res = 0;
        } catch (Exception e) {
            SystemManager.getLogger().log(MenuManager.class.getName() + ".createMenuItem", e);
            res = 0;
        }
        if (res == 0)
            return null;
        MenuItem mi = new MenuItem(name, url, order, rights, hook);
        items.add(mi);
        Collections.sort(items, new MenuItemComparator());
        return mi;
    }

    public static synchronized MenuItem getMenuItem(String name) {
        try {
            return (MenuItem) ConnectionManager.executeQuery(SELECT_MENU_ITEM,
                                                             new Object[]{name},
                                                             handler)[0];
        } catch (SQLException e) {
            SystemManager.getLogger().log(MenuManager.class.getName() + ".getMenuItem", e);
            return null;
        }
    }

    public static synchronized int removeMenuItem(String name) {
        int res;
        try {
            res = ConnectionManager.executeUpadte(DELETE_MENU_ITEM, new Object[]{name});
        } catch (SQLException e) {
            SystemManager.getLogger().log(MenuManager.class.getName() + ".removeMenuItem", e);
            return -1;
        }
        int i = 0;
        for (; i < items.size(); i++)
            if (items.get(i).getName().equalsIgnoreCase(name))
                break;
        items.remove(i);
        return res;
    }

    public static synchronized int updateMenuItem(int id, String name, String url,
                                                  String rights, int order) {
        int res;
        try {
            if (name.length() == 0)
                name = null;
            res = ConnectionManager.executeUpadte(UPDATE_MENU_ITEM,
                                                  new Object[]{name, url, order, rights, id});
        } catch (SQLException e) {
            SystemManager.getLogger().log(MenuManager.class.getName() + ".updateMenuItem", e);
            return 0;
        }
        for (MenuItem mi : items)
            if (id == mi.getId()) {
                mi.setName(name);
                mi.setUrl(url);
                mi.setOrder(order);
                mi.setRights(RightsType.valueOf(rights));
                break;
            }
        Collections.sort(items, new MenuItemComparator());
        return res;
    }

    public static synchronized MenuItem[] getMenu(RightsType rights) {
        int from = findRightsStartIndex(rights);
        List<MenuItem> lst = new ArrayList<MenuItem>();
        for (int i = from; i < items.size(); i++) {
            MenuItem mi = items.get(i);
            if (mi.getRights() != rights)
                break;
            if (mi.getName() != null)
                lst.add(mi);
        }
        MenuItem[] res = new MenuItem[lst.size()];
        return lst.toArray(res);
    }

    /**
     * Бинарный поиск начала подмассива линок с правами <code>security</code>
     *
     * @param rights
     * @return Индекс начиная с которого (включительно) идут линки с заданными парвами
     */
    private static int findRightsStartIndex(RightsType rights) {
        if (rights == items.get(0).getRights())
            return 0;
        int sidx = 0;
        int fidx = items.size();
        int i = 0;
        while (i++ <= items.size()) {
            int idx = (sidx + fidx) / 2;
            if (items.get(idx).getRights() == rights &&
                items.get(idx - 1).getRights() != rights)
                return idx;
            if (RightsType.compareRights(rights, items.get(idx).getRights()) <= 0)
                fidx = idx;
            else
                sidx = idx;
        }
        return i;
    }

    public static synchronized String toStringS() {
        return ArrayUtils.toString(items);
    }

    public static synchronized void load() {
        try {
            // загрузка кэша
            Object[] res = ConnectionManager.executeQuery(SELECT_MENU,
                                                          ACSConstants.ZERO_ARRAY,
                                                          handler);
            Arrays.sort(res, new MenuItemComparator());
            items.clear();
            ArrayUtils.putArrayToList(res, items);
        } catch (SQLException e) {
            SystemManager.getLogger().log(MenuManager.class.getName() + ".static", e);
        }
    }

    public static synchronized void reload() {
        load();
    }

    public ACSUIEditable getEditable(SimpleRequestContext src) {
        return getMenuItem(src.getInt("id"));
    }

    private MenuItem getMenuItem(int id) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_MENU_ITEM_BY_ID,
                                                          new Object[]{id},
                                                          handler);
            if (res.length > 0)
                return (MenuItem) res[0];
            return null;
        } catch (SQLException e) {
            SystemManager.getLogger().log(MenuManager.class.getName() + ".getMenuItem", e);
            return null;
        }
    }

    public ACSUIEditable updatetEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        MenuItem item = null;
        if (cp[0] && cp[1] && cp[2] && cp[3]) {
            updateMenuItem(src.getInt("id"), src.getString("name"), src.getString("url"),
                           src.getString("security"), src.getInt("order"));
            item = getMenuItem(src.getInt("id"));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пункт меню навигации обновлён", StatusMessageType.OK_MESSAGE));
        } else {
            if (!cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректное зназвание", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный URL", StatusMessageType.ERROR_MESSAGE));
            if (!cp[2])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректные права", StatusMessageType.ERROR_MESSAGE));
            if (!cp[3])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный порядок", StatusMessageType.ERROR_MESSAGE));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пункт меню навигации не обновлён", StatusMessageType.ERROR_MESSAGE));
        }
        return item;
    }

    public ACSUIEditable insertEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        MenuItem item = null;
        if (cp[0] && cp[1] && cp[2] && cp[3]) {
            item = createMenuItem(src.getString("name"), src.getString("url"),
                                  src.getInt("order"), RightsType.valueOf(src.getString("security")));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пункт меню навигации добавлен", StatusMessageType.OK_MESSAGE));
        } else {
            if (!cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректное зназвание", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный URL", StatusMessageType.ERROR_MESSAGE));
            if (!cp[2])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректные права", StatusMessageType.ERROR_MESSAGE));
            if (!cp[3])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный порядок", StatusMessageType.ERROR_MESSAGE));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пункт меню навигации не добавлен", StatusMessageType.ERROR_MESSAGE));
        }
        return item;
    }

    public ACSUIEditable deleteEditable(SimpleRequestContext src) {
        MenuItem i = getMenuItem(src.getInt("id"));
        int res = removeMenuItem(src.getInt("id"));
        if (res == 0) {
            i = null;
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пункт меню навигации не удалён", StatusMessageType.ERROR_MESSAGE));
        } else {
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пункт меню навигации удалён", StatusMessageType.OK_MESSAGE));
        }
        return i;
    }

    private int removeMenuItem(int id) {
        int res;
        try {
            res = ConnectionManager.executeUpadte(DELETE_MENU_ITEM_BY_ID, new Object[]{id});
        } catch (SQLException e) {
            SystemManager.getLogger().log(MenuManager.class.getName() + ".removeMenuItem", e);
            return 0;
        }
        int i = 0;
        for (; i < items.size(); i++)
            if (items.get(i).getId() == id)
                break;
        items.remove(i);
        return res;
    }

    public String[] getColNames() {
        return new String[]{"Имя", "URL", "Необходимые права", "Порядок"};
    }

    public ACSUIEditable[] getEditables(SimpleRequestContext src) {
        ACSUIEditable[] res = new ACSUIEditable[items.size()];
        return items.toArray(res);
    }

    public String getType() {
        return "menu";
    }

    public boolean[] checkInputData(ACSRequestContext ctx) {
        boolean[] res = new boolean[]{true, true, true, true};
        res[0] = Pattern.matches("^[\\p{Alnum}[\\=\\?;&_\\.][А-Яа-я]]*$", ctx.getString("name"));
        res[1] = Pattern.matches("^[\\p{Alnum}[\\=\\?;&_\\.]]+$", ctx.getString("url"));
        try {
            RightsType.valueOf(ctx.getString("security"));
        } catch (Exception e) {
            res[2] = false;
        }
        try {
            Integer.parseInt(ctx.getString("order"));
        } catch (NumberFormatException e) {
            res[3] = false;
        }
        return res;
    }

    public String getManagmentLabel() {
        return "Управление пунктами меню навигации";
    }

    public String getAddLabel() {
        return "Добавление пункта меню навигации";
    }

    public String getUpdateLabel() {
        return "Обновление пункта меню навигации";
    }

    public ACSUIEditable getEditable() {
        return new MenuItem("", "", -1, RightsType.RIGHTS_NONE, -1);
    }
}
