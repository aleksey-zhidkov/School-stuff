package ru.lexx.acsystem.backend.user;

import ru.jdev.common.ArrayUtils;
import ru.jdev.crypt.lca2005.LCA2005;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.StringRowHandler;
import ru.jdev.utils.string.Converter;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.constants.CommonsHandlers;
import ru.lexx.acsystem.backend.messenger.Messenger;
import ru.lexx.acsystem.backend.messenger.UserRecipientFactory;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.TaskPartitionsManager;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditor;
import ru.lexx.acsystem.backend.uiediatble.ACSUIFilter;
import ru.lexx.acsystem.backend.utils.ACSStringUtils;
import ru.lexx.acsystem.webinterface.ACSRequestContext;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.SelectElement;
import ru.jdev.html.forms.elements.SubmitElement;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 13:47:32
 */
public class UserManager implements ACSUIEditor, ACSUIFilter {

    private static final UserRowHandler handler = new UserRowHandler();
    private static final StringRowHandler shandler = new StringRowHandler("login");

    private static final String INSERT_USER = "INSERT INTO users " +
                                              "(id,login,password,fio,email,sys_group,reg_date) " +
                                              "VALUES (?,?,?,?,?,?,?)";

    private static final String SELECT_USER = "SELECT u.id, login, fio, email," +
                                              "         (SELECT security" +
                                              "                 FROM system_groups" +
                                              "                      WHERE id = sys_group" +
                                              "         ) security," +
                                              "         (SELECT prog_lang " +
                                              "                 FROM system_groups " +
                                              "                      WHERE id = sys_group" +
                                              "         ) prog_lang, " +
                                              "         (SELECT name " +
                                              "                 FROM system_groups " +
                                              "                      WHERE id = sys_group) group_name, " +
                                              "         reg_date, sys_group " +
                                              "       FROM users u" +
                                              "            WHERE login = ? AND password = ?";

    private static final String DELETE_USER = "DELETE FROM users " +
                                              "WHERE id = ?";

    private static final String UPDATE_USER = "UPDATE users SET " +
                                              "  login = ?, " +
                                              "  password = ?, " +
                                              "  fio = ?, " +
                                              "  sys_group = ?, " +
                                              "  email = ? " +
                                              "WHERE login = ?";

    private static final String UPDATE_USER_WITHOUT_PASS = "UPDATE users SET " +
                                                           "  login = ?, " +
                                                           "  fio = ?, " +
                                                           "  sys_group = ?, " +
                                                           "  email = ? " +
                                                           "WHERE login = ?";

    private static final String HAVE_USER = "SELECT count(*) FROM users " +
                                            "WHERE login = ?";

    private static final String SELECT_ALL_USERS = "SELECT login FROM users";

    private static final String SELECT_USER_WITHOUT_PASS = "SELECT u.id, login, fio, email," +
                                                           "         (SELECT security" +
                                                           "                 FROM system_groups" +
                                                           "                      WHERE id = sys_group" +
                                                           "         ) security," +
                                                           "         (SELECT prog_lang " +
                                                           "                 FROM system_groups " +
                                                           "                      WHERE id = sys_group" +
                                                           "         ) prog_lang, " +
                                                           "         (SELECT name " +
                                                           "                 FROM system_groups" +
                                                           "                      WHERE id = sys_group) group_name, " +
                                                           "         reg_date, sys_group " +
                                                           "       FROM users u " +
                                                           "            WHERE login = ?";

    private static final String SELECT_ALL_ACCAUNTS = "SELECT u.id, login, fio, email, " +
                                                      "         (SELECT security" +
                                                      "                 FROM system_groups" +
                                                      "                      WHERE id = sys_group" +
                                                      "         ) security," +
                                                      "         (SELECT prog_lang " +
                                                      "                 FROM system_groups " +
                                                      "                      WHERE id = sys_group" +
                                                      "         ) prog_lang, " +
                                                      "         (SELECT name " +
                                                      "                 FROM system_groups" +
                                                      "                      WHERE id = sys_group) group_name, " +
                                                      "         reg_date, sys_group " +
                                                      "       FROM users u WHERE id NOT IN (SELECT id FROM disabled)";

    private static final String SELECT_ACCAUNT_BY_ID = "SELECT u.id, login, fio, email, " +
                                                       "         (SELECT security" +
                                                       "                 FROM system_groups" +
                                                       "                      WHERE id = sys_group " +
                                                       "         ) security," +
                                                       "         (SELECT prog_lang " +
                                                       "                 FROM system_groups " +
                                                       "                      WHERE id = sys_group " +
                                                       "         ) prog_lang, " +
                                                       "         (SELECT name " +
                                                       "                 FROM system_groups " +
                                                       "                      WHERE id = sys_group) group_name, " +
                                                       "         reg_date, sys_group " +
                                                       "       FROM users u " +
                                                       "            WHERE id = ?";

    private static final String SELECT_STUDENTS = "SELECT u.id, sg.id, login, fio, email, " +
                                                  "       (SELECT security FROM system_groups sg2 WHERE sg2.id = u.sys_group) security, " +
                                                  "       (SELECT prog_lang FROM system_groups sg2 WHERE sg2.id = u.sys_group ) prog_lang, " +
                                                  "       (SELECT name FROM system_groups sg2 WHERE sg2.id = u.sys_group) group_name, " +
                                                  "       reg_date, sys_group " +
                                                  "       FROM users u, system_groups sg " +
                                                  "            WHERE u.sys_group = sg.id AND sg.security like '%RIGHTS_USER%'";

    private static final String SELECT_ACCAUNTS_BY_GROUP = "SELECT u.id, login, fio, email, " +
                                                           "         (SELECT security" +
                                                           "                 FROM system_groups" +
                                                           "                      WHERE id = sys_group " +
                                                           "         ) security," +
                                                           "         (SELECT prog_lang " +
                                                           "                 FROM system_groups " +
                                                           "                      WHERE id = sys_group " +
                                                           "         ) prog_lang, " +
                                                           "         (SELECT name " +
                                                           "                 FROM system_groups " +
                                                           "                      WHERE id = sys_group) group_name, " +
                                                           "         reg_date, sys_group " +
                                                           "       FROM users u " +
                                                           "            WHERE sys_group = ? AND id NOT IN (SELECT id FROM disabled)";

    // Специальный аккаунт обозначающий незарегестрированного (не залогинненого) пользователя
    public static final UserAccaunt noneAccaunt;

    /* Супер-аккаунт администратора. Всё что относится к нему - относиться ко всем администраторам.
    * Например сообщение отправленное данному пользователю появиться как новое сообщение у всех администраторов
    * системы.
    */
    public static final UserAccaunt TESH_SUPPORT;

    /**
     * Пользователь представляющий систему. Например сообщения о том, что пользователь закончил программу
     * посылаются от этого пользователя пользователю TECH_SUPPORT
     */
    public static final UserAccaunt SYSTEM;

    static {
        noneAccaunt = new UserAccaunt("none", "", "", SystemGroupsManager.UNREGISTERED_GROUP, 0, 10, new Date());
        TESH_SUPPORT = getAccaunt(1000000010);
        UserAccaunt tmp = getAccaunt("ACS");
        if (tmp == null) {
            SYSTEM = createAccaunt("ACS", "", "", "", SystemGroupsManager.ADMIN_GROUP);
            try {
                ConnectionManager.executeUpadte("INSERT INTO disabled (id) VALUES (?)", new Object[]{new Integer(SYSTEM.getId())});
            } catch (SQLException e) {
                SystemManager.getLogger().log(UserManager.class.getName() + ".static", e);
            }
        }
        else
            SYSTEM = tmp;
    }

    public static synchronized UserAccaunt createAccaunt(String login, String pass, String fio,
                                                         String email, SystemGroup group) {
        int res;
        try {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            res = ConnectionManager.executeUpadte(INSERT_USER,
                                                  new Object[]{SystemHookGenerator.getNextHook(), login,
                                                               new String(LCA2005.crypt(pass), "cp1251"), fio,
                                                               email, group.getId(), ts});
        }
        catch (SQLException e) {
            if (e.getMessage().indexOf("Connection refused") != -1)
                SystemManager.setIsSystemFail(true);
            SystemManager.getLogger().log(UserManager.class.getName() + ".createAccaunt", e);
            res = 0;
        }
        catch (UnsupportedEncodingException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".createAccaunt", e);
            res = 0;
        }
        catch (Exception e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".createAccaunt", e);
            res = 0;
        }
        if (res == 0)
            return noneAccaunt;
        return getAccaunt(login, pass);
    }

    public static synchronized UserAccaunt getAccaunt(String login, String pass) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_USER,
                                                          new Object[]{Converter.winToUtf(login), new String(LCA2005.crypt(pass), "cp1251")},
                                                          handler);
            if (res.length == 0)
                return noneAccaunt;
            UserAccaunt accaunt = (UserAccaunt) res[0];
            int sam = 0;
            if (accaunt.getSys_group().getRights().contains(RightsType.RIGHTS_ADMIN))
                sam = Messenger.getUserNewMessages(TESH_SUPPORT);
            accaunt.setNewMessages(Messenger.getUserNewMessages(accaunt) + sam);
            Messenger.addListener(accaunt);
            if (!accaunt.getSys_group().getRights().contains(RightsType.RIGHTS_USER))
                accaunt.setPartition(TaskPartitionsManager.finished);
            else
                accaunt.setPartition(TaskPartitionsManager.getUserPartition(accaunt));
            return accaunt;
        }
        catch (SQLException e) {
            if (e.getMessage().indexOf("Connection refused") != -1)
                SystemManager.setIsSystemFail(true);
            SystemManager.getLogger().log(UserManager.class.getName() + ".getAccaunt", e);
        }
        catch (UnsupportedEncodingException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".getAccaunt", e);
        }
        return noneAccaunt;
    }

    public static synchronized int deleteAccaunt(int id) {
        try {
            return ConnectionManager.executeUpadte(DELETE_USER,
                                                   new Object[]{id});
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".deleteAccaunt", e);
            return 0;
        }
    }

    public static synchronized int upadteAccaunt(String login, String pass, String fio,
                                                 int sys_group, String email, String old_login) {
        try {
            UserAccaunt old = getAccaunt(old_login);
            String str = (pass.length() == 0) ? UPDATE_USER_WITHOUT_PASS : UPDATE_USER;
            Object[] params = (pass.length() == 0) ? new Object[]{login,
                                                                  fio,
                                                                  sys_group,
                                                                  email,
                                                                  old_login}
                              : new Object[]{login,
                                             new String(LCA2005.crypt(pass), "cp1251"),
                                             fio,
                                             sys_group,
                                             email,
                                             old_login};
            int res = ConnectionManager.executeUpadte(str, params);
            if (old.getSys_group().getId() != SystemGroupsManager.getGroupById(sys_group).getId()) {
                Map mParams = new HashMap();
                mParams.put("old_group",old.getSys_group().getName());
                mParams.put("new_group",SystemGroupsManager.getGroupById(sys_group).getName());
                try {
                    Messenger.sendMessage(ACSStringUtils.processTemplate("events/change_group",mParams),SYSTEM, new UserRecipientFactory(login));
                } catch (IOException e) {
                    SystemManager.getLogger().log(UserManager.class.getName()+".updateAccaunt",e);
                }
            }
            return res;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".updateUser", e);
        }
        catch (UnsupportedEncodingException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".", e);
        }
        return 0;
    }

    public static synchronized boolean haveUser(String login) throws SQLException {
        return ((Integer) ConnectionManager.executeQuery(HAVE_USER,
                                                         new Object[]{login},
                                                         CommonsHandlers.INTEGER_ROW_HANDLER_COUNT)[0]).intValue() != 0;
    }

    public static synchronized String[] getAllUsers() {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_ALL_USERS, ACSConstants.ZERO_ARRAY, shandler);
            String[] sres = new String[res.length];
            new ArrayUtils<String>().castArray(sres, res);
            return sres;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".getAllUsers", e);
        }
        return new String[0];
    }

    /**
     * Оборачивает строковое предстовление логина в объект аккаунта. Все остальные поля обнулены
     *
     * @param login Логин аккаунта
     * @return Возвращяет аккаунт в котором обнулены все поля кроме <code>login</code>
     */
    public static synchronized UserAccaunt getAccaunt(String login) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_USER_WITHOUT_PASS,
                                                          new Object[]{login},
                                                          handler);
            if (res.length == 0)
                return null;
            UserAccaunt a = (UserAccaunt) res[0];
            return a;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".getAccaunt", e);
            return null;
        }
    }

    public static synchronized UserAccaunt[] getAllAccauns() {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_ALL_ACCAUNTS, ACSConstants.ZERO_ARRAY, handler);
            UserAccaunt[] ares = new UserAccaunt[res.length];
            ArrayUtils<UserAccaunt> au = new ArrayUtils<UserAccaunt>();
            au.castArray(ares, res);
            return ares;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".getAllAccaunts", e);
            return new UserAccaunt[0];
        }
    }

    public static synchronized UserAccaunt getAccaunt(int id) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_ACCAUNT_BY_ID,
                                                          new Object[]{id},
                                                          handler);
            if (res.length == 0)
                return null;
            return (UserAccaunt) res[0];
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".getAccaunt(int)", e);
            return null;
        }
    }

    public boolean[] checkInputData(ACSRequestContext ctx) {
        boolean[] cp = {true, true, true, true, true};
        cp[0] = Pattern.matches("^[\\p{Alnum}]+$", ctx.getString("login"));
        cp[1] = Pattern.matches("^[\\p{Alnum}]+$", ctx.getString("pass1")) && (ctx.getString("pass1").compareTo(ctx.getString("pass2")) == 0);
        cp[2] = Pattern.matches("^[\\p{Alnum}_]+@[\\p{Alnum}_]+\\.[\\p{Alnum}]{2,4}$", ctx.getString("email"));
        cp[3] = ctx.getString("fio").length() > 0;
        // check for existing login
        try {
            if (UserManager.haveUser(ctx.getString("login")))
                cp[4] = false;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".checkInputData", e);
            cp[4] = false;
        }
        return cp;
    }

    public String getManagmentLabel() {
        return "Управление пользователями";
    }

    public String getAddLabel() {
        return "Добавление пользователя";
    }

    public String getUpdateLabel() {
        return "Обновление пользователя";
    }

    public ACSUIEditable getEditable(SimpleRequestContext src) {
        return getAccaunt(src.getInt("id"));
    }

    public ACSUIEditable updatetEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (src.getString("login").equalsIgnoreCase(src.getString("old_login")))
            cp[4] = true;
        if (src.getString("pass1").length() == 0 && src.getString("pass2").length() == 0)
            cp[1] = true;
        UserAccaunt accaunt = null;
        if (cp[0] && cp[1] && cp[2] && cp[3] && cp[4]) {
            upadteAccaunt(src.getString("login"), src.getString("pass1"),
                          src.getString("fio"), src.getInt("group"), src.getString("email"),
                          src.getString("old_login"));
            accaunt = getAccaunt(src.getString("login"));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пользователь обновлён", StatusMessageType.OK_MESSAGE));
        }
        if (!cp[0])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный логин", StatusMessageType.ERROR_MESSAGE));
        if (!cp[1])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пароль и его подтверждине не совпадают или равны 0", StatusMessageType.ERROR_MESSAGE));
        if (!cp[2])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Неверный электронный адрес", StatusMessageType.ERROR_MESSAGE));
        if (!cp[3])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Не указнны ФИО", StatusMessageType.ERROR_MESSAGE));
        if (!cp[4])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пользователь с логином" + src.getString("login") + " уже существует", StatusMessageType.ERROR_MESSAGE));
        return accaunt;
    }

    public ACSUIEditable insertEditable(SimpleRequestContext src) {
        String login = src.getString("login").trim();
        String pass1 = src.getString("pass1").trim();
        String email = src.getString("email").trim();
        String fio = src.getString("fio").trim();
        int id = src.getInt("group");

        boolean[] cp = checkInputData((ACSRequestContext) src);

        // putting results to context
        if (!cp[0])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный логин", StatusMessageType.ERROR_MESSAGE));
        if (!cp[1])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пароль и его подтверждине не совпадают или равны 0", StatusMessageType.ERROR_MESSAGE));
        if (!cp[2])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Неверный электронный адрес", StatusMessageType.ERROR_MESSAGE));
        if (!cp[3])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Не указнны ФИО", StatusMessageType.ERROR_MESSAGE));
        if (!cp[4])
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пользователь с логином" + src.getString("login") + " уже существует", StatusMessageType.ERROR_MESSAGE));
        UserAccaunt accaunt = null;
        if (cp[0] && cp[1] && cp[2] && cp[3] && cp[4]) {
            accaunt = UserManager.createAccaunt(login, pass1, fio, email,
                                                SystemGroupsManager.getGroupById(id));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пользователь обновлён", StatusMessageType.OK_MESSAGE));
        }
        return accaunt;
    }

    public ACSUIEditable deleteEditable(SimpleRequestContext src) {
        UserAccaunt a = getAccaunt(src.getInt("id"));
        int res = deleteAccaunt(src.getInt("id"));
        if (res == 0)
            a = null;
        if (a != null)
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пользователь " + a.getLabel() + " удалён", StatusMessageType.OK_MESSAGE));
        else
            ((ACSRequestContext) src).addMessage(new StatusMessage("Пользователь не удалён, попробуйте позже", StatusMessageType.ERROR_MESSAGE));
        return a;
    }

    public ACSUIEditable[] getEditables(SimpleRequestContext src) {
        return getAllAccauns();
    }

    public String[] getColNames() {
        return new String[]{"Логин", "ФИО", "Е-майл", "Зарегестрирован", "Группа"};
    }

    public FormImpl getFilterForm() {
        FormImpl form = new FormImpl("acsform");
        form.setName("filterusers_form");
        ((FormImpl) form).setFormUserName("Форма фильтрации пользователей");
        SelectElement se = new SelectElement(SystemGroupsManager.getGroupOptins(), "filter", "Группа", "");
        se.addOption(new SelectElement.Option("Нет", "0"));
        form.addElement(se);
        form.addElement(new SubmitElement("submit", "Фильтровать"));
        return form;
    }

    public ACSUIEditable[] getFilteredEditables(SimpleRequestContext src) {
        if (src.getInt("filter") != 0)
            return UserManager.getUsersbyGroup(src.getInt("filter"));
        else
            return getEditables(src);
    }

    public String getFilterMessage(ACSRequestContext ctx) {
        return "Отфильтрованно по группе " + SystemGroupsManager.getGroupById(ctx.getInt("filter")).getName();
    }

    public String getType() {
        return "user";
    }

    public ACSUIEditable getEditable() {
        return new UserAccaunt("", "", "", SystemGroupsManager.DEFAULT_GROUP, 0, 0, new Date());
    }

    public static UserAccaunt[] getStudents() {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_STUDENTS, ACSConstants.ZERO_ARRAY, handler);
            UserAccaunt[] ares = new UserAccaunt[res.length];
            ArrayUtils<UserAccaunt> utils = new ArrayUtils<UserAccaunt>();
            utils.castArray(ares, res);
            return ares;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".getStudents", e);
            return new UserAccaunt[]{};
        }
    }

    public static UserAccaunt[] getUsersbyGroup(int group_id) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_ACCAUNTS_BY_GROUP, new Object[]{group_id}, handler);
            UserAccaunt[] ares = new UserAccaunt[res.length];
            ArrayUtils<UserAccaunt> utils = new ArrayUtils<UserAccaunt>();
            utils.castArray(ares, res);
            return ares;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(UserManager.class.getName() + ".getStudents", e);
            return new UserAccaunt[]{};
        }
    }
}
