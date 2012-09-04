package ru.lexx.acsystem.backend.user;

import ru.jdev.common.ArrayUtils;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.utils.db.ConnectionManager;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditor;
import ru.lexx.acsystem.webinterface.ACSRequestContext;
import ru.jdev.html.forms.elements.SelectElement;

import java.sql.SQLException;
import java.util.regex.Pattern;

public class SystemGroupsManager implements ACSUIEditor {
    public static final SystemGroup DEFAULT_GROUP;
    public static final SystemGroup ADMIN_GROUP;
    public static final SystemGroup UNREGISTERED_GROUP;

    private static final SystemGroupRowHandler handler = new SystemGroupRowHandler();

    private static final String SELECT_GROUP_BY_NAME = "SELECT security, name, prog_lang, id " +
                                                       "       FROM system_groups " +
                                                       "            WHERE name = ?";

    private static final String SELECT_ALL_GROUPS = "SELECT security, name, prog_lang, id " +
                                                    "       FROM system_groups";

    private static final String SELECT_GROUP_BY_ID = "SELECT security, name, prog_lang, id " +
                                                     "       FROM system_groups " +
                                                     "            WHERE id = ?";

    private static final String UPDATE_GROUP = "UPDATE system_groups " +
                                               "       SET name = ?, security = ?, prog_lang = ? " +
                                               "           WHERE id = ?";

    private static final String INSERT_GROUP = "INSERT INTO system_groups (id,name,security,prog_lang) " +
                                               "       VALUES (?,?,?,?)";

    private static final String DELETE_GROUP = "DELETE FROM system_groups " +
                                               "       WHERE id = ?";


    static {
        DEFAULT_GROUP = getGroupByName("Пользователи");
        ADMIN_GROUP = getGroupByName("Администраторы");
        UNREGISTERED_GROUP = getGroupByName("Незарегестрированные");
    }

    public static SystemGroup getGroupByName(String name) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_GROUP_BY_NAME, new Object[]{name}, handler);
            if (res.length == 0)
                return DEFAULT_GROUP;
            return (SystemGroup) res[0];
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(SystemGroupsManager.class.getName() + ".getGroupByName(String)", e);
            return null;
        }
    }

    public static SystemGroup getGroupById(int id) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_GROUP_BY_ID, new Object[]{id}, handler);
            if (res.length == 0)
                return DEFAULT_GROUP;
            return (SystemGroup) res[0];
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(SystemGroupsManager.class.getName() + ".getGroupById(String)", e);
            return null;
        }
    }

    public static SystemGroup[] getAllGroups() {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_ALL_GROUPS, ACSConstants.ZERO_ARRAY, handler);
            SystemGroup[] sgres = new SystemGroup[res.length];
            ArrayUtils<SystemGroup> au = new ArrayUtils<SystemGroup>();
            au.castArray(sgres, res);
            return sgres;
        } catch (SQLException e) {
            SystemManager.getLogger().log(SystemGroupsManager.class.getName() + "getAllGroups", e);
        }
        return new SystemGroup[0];
    }

    public static SelectElement.Option[] getGroupOptins() {
        SystemGroup[] groups = getAllGroups();
        SelectElement.Option[] ops = new SelectElement.Option[groups.length];
        for (int i = 0; i < ops.length; i++) {
            ops[i] = new SelectElement.Option(groups[i].getName(), groups[i].getId() + "");
        }
        return ops;
    }

    public ACSUIEditable getEditable(SimpleRequestContext src) {
        return getGroupById(src.getInt("id"));
    }

    public ACSUIEditable updatetEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1] && cp[2]) {
            SystemGroup sg = getGroupById(src.getInt("id"));
            try {
                StringBuffer rights = new StringBuffer();

                String[] rts;
                Object or = src.getObject("security");
                if (or instanceof String[])
                    rts = (String[]) or;
                else {
                    rts = new String[]{(String) or};
                }
                for (String r : rts) {
                    rights.append(r).append(",");
                }
                rights.deleteCharAt(rights.length() - 1);
                ConnectionManager.executeUpadte(UPDATE_GROUP, new Object[]{src.getString("name"), rights.toString(),
                                                                           src.getString("prog_lang"), src.getInt("id")});
                ((ACSRequestContext) src).addMessage(new StatusMessage("Группа обновлена", StatusMessageType.OK_MESSAGE));
                return sg;
            } catch (SQLException e) {
                SystemManager.getLogger().log(getClass().getName() + ".updateEditable", e);
                ((ACSRequestContext) src).addMessage(new StatusMessage("Группа не обновлена", StatusMessageType.ERROR_MESSAGE));
                return null;
            }
        } else {
            if (!cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не указанно название", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный список прав", StatusMessageType.ERROR_MESSAGE));
            if (!cp[2])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный язык программирования", StatusMessageType.ERROR_MESSAGE));

            ((ACSRequestContext) src).addMessage(new StatusMessage("Группа не обновлена", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public ACSUIEditable insertEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1] && cp[2]) {
            long id;
            try {
                id = SystemHookGenerator.getNextHook();
                StringBuffer rights = new StringBuffer();
                Object o = src.getObject("security");
                String[] rts;
                if (o instanceof String[])
                    rts = (String[]) o;
                else
                    rts = new String[]{(String) o};
                for (String r : rts) {
                    rights.append(r).append(",");
                }
                rights.deleteCharAt(rights.length() - 1);
                ConnectionManager.executeUpadte(INSERT_GROUP,
                                                new Object[]{id, src.getString("name"), rights.toString(),
                                                             src.getString("prog_lang")}
                );
                ((ACSRequestContext) src).addMessage(new StatusMessage("Группа добавленна", StatusMessageType.OK_MESSAGE));
                return getGroupById((int) id);
            } catch (SQLException
                    e) {
                SystemManager.getLogger().log(getClass().getName() + ".insertEditable", e);
                ((ACSRequestContext) src).addMessage(new StatusMessage("Группа не добавленна по причине внутренней ошибки", StatusMessageType.ERROR_MESSAGE));
                return null;
            } catch (Exception
                    e) {
                SystemManager.getLogger().log(getClass().getName() + ".insertEditable", e);
                ((ACSRequestContext) src).addMessage(new StatusMessage("Группа не добавленна по причине внутренней ошибки", StatusMessageType.ERROR_MESSAGE));
                return null;
            }
        } else {
            if (!cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректное название", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректные права", StatusMessageType.ERROR_MESSAGE));
            if (!cp[2])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный язык программирования", StatusMessageType.ERROR_MESSAGE));

            ((ACSRequestContext) src).addMessage(new StatusMessage("Группа не добавленна", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public ACSUIEditable deleteEditable
            (SimpleRequestContext
                    src) {
        SystemGroup sg = getGroupById(src.getInt("id"));
        try {
            int res = ConnectionManager.executeUpadte(DELETE_GROUP, new Object[]{sg.getId()});
            if (res == 0)
                sg = null;
        } catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".deleteEditable", e);
            ((ACSRequestContext) src).addMessage(new StatusMessage("Группа не удалёна, по причине внутренней ошибки", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
        if (sg != null)
            ((ACSRequestContext) src).addMessage(new StatusMessage("Группа " + sg.getLabel() + " удалёна", StatusMessageType.OK_MESSAGE));
        else
            ((ACSRequestContext) src).addMessage(new StatusMessage("Группа не удалёна, попробуйте позже", StatusMessageType.ERROR_MESSAGE));
        return sg;
    }

    public String[] getColNames
            () {
        return new String[]{"Название", "Права", "Язык программирования"};
    }

    public ACSUIEditable[] getEditables
            (SimpleRequestContext
                    src) {
        return getAllGroups();
    }

    public String getType
            () {
        return "security";
    }

    public boolean[] checkInputData
            (ACSRequestContext
                    ctx) {
        boolean[] res = {true, true, false};
        res[0] = Pattern.matches("^[А-Яа-яA-Za-z0-9]+$", ctx.getString("name"));

        try {
            Object o = ctx.getObject("security");
            String[] rts;
            if (o instanceof String[])
                rts = (String[]) o;
            else
                rts = new String[]{(String) o};
            for (String s : rts) {
                RightsType.valueOf(s);
            }
        } catch (Exception e) {
            res[1] = false;
        }

        ProgLanguage[] langs = ProgLanguage.getAll();
        for (ProgLanguage lang : langs) {
            if (lang.getName().equalsIgnoreCase(ctx.getString("prog_lang"))) {
                res[2] = true;
            }
        }
        return res;
    }

    public String getManagmentLabel() {
        return "Управление группами";
    }

    public String getAddLabel() {
        return "Добавление группы";
    }

    public String getUpdateLabel() {
        return "Обновление группы";
    }

    public ACSUIEditable getEditable
            () {
        return new SystemGroup(new Rights(RightsType.RIGHTS_NONE), "", ProgLanguage.NONE, 0);
    }

}
