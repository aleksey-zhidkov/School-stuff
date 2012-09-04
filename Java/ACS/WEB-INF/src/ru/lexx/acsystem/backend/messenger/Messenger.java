package ru.lexx.acsystem.backend.messenger;

import ru.jdev.common.ArrayUtils;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.jdev.utils.string.StringUtils;
import ru.jdev.utils.string.Converter;
import ru.lexx.acsystem.backend.constants.CommonsHandlers;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 28.01.2006
 * Time: 19:38:50
 */
public class Messenger {

    private static final RowHandler handler = new MessageRowHandler();

    private static ArrayList<NewMessageListener> listeners = new ArrayList<NewMessageListener>();

    private static final String INSERT_MESSAGE = "INSERT INTO message (id,from_user_id,to_user_id,message_body,sent_date) " +
                                                 "VALUES (?,?,?,?,?)";

    private static final String CLEARING = "DELETE FROM message WHERE from_user_id = ? AND message_body = ? AND sent_date >= ?";

    private static final String SELECT_IN_MESSAGES = "SELECT m.id, (select login from users where id = m.from_user_id) from_user, (select login from users where id = m.to_user_id) to_user, m.message_body, m.sent_date, " +
                                                     "       m.view_date, m.read_date, m.folder " +
                                                     "FROM message m WHERE to_user_id = ? ORDER BY m.sent_date DESC";

    private static final String SELECT_OUT_MESSAGES = "SELECT m.id, (select login from users where id = m.from_user_id) from_user, (select login from users where id = m.to_user_id) to_user, m.message_body, m.sent_date, " +
                                                      "       m.view_date, m.read_date, m.folder " +
                                                      "FROM message m WHERE from_user_id = ? ORDER BY m.sent_date DESC";

    private static final String SELECT_NEW_MESSAGES = "SELECT count(*) FROM message m " +
                                                      "WHERE m.to_user_id = ? AND m.view_date IS NULL";

    private static final String VIEW_MESSAGES = "UPDATE message SET view_date = ? WHERE to_user_id = ? AND " +
                                                "view_date IS NULL";

    private static final String SELECT_MESSAGE = "SELECT (select login from users where id = m.from_user_id) from_user, (select login from users where id = m.to_user_id) to_user, message_body, sent_date, view_date, read_date, folder,id " +
                                                 "FROM message m WHERE id = ?";

    private static final String READ_MESSAGE = "UPDATE message SET read_date = ? WHERE id = ?";

    private static final String DELETE_MESSAGE = "DELETE FROM message WHERE id = ?";

    /**
     * ќтсылает сообщение пользовател€м полученным из фабрики получателей и оповещ€ет об этом слушателей.
     *
     * @param text “екст сообщени€
     * @param from ѕользователь от чьего им€ рассылаютс€ собщени€
     * @param rf   ‘абрика получателей сообщени€
     * @return ¬озвращ€ет массив получателей которым сообщение Ќ≈ доставленно.
     *         ¬ случае нормальной работы системы длина данного массива равна 0.
     */
    public static synchronized boolean sendMessage(String text, UserAccaunt from, RecipientFactory rf) {
        ArrayList<UserAccaunt> success = new ArrayList<UserAccaunt>();
        UserAccaunt[] recipients = rf.getRecipients();
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        text = Converter.getHTMLString(text);
        try {
            for (UserAccaunt a : recipients) {
                int res = ConnectionManager.executeUpadte(INSERT_MESSAGE,
                                                          new Object[]{SystemHookGenerator.getNextHook(), from.getId(),
                                                                       a.getId(), text, startTime});
                if (res > 0)
                    success.add(a);
            }
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(Messenger.class.getName() + ".sendMessage", e);
            try {
                ConnectionManager.executeUpadte(CLEARING,
                                                new Object[]{from.getId(), text, startTime});
            }
            catch (SQLException e1) {
                SystemManager.getLogger().log(Messenger.class.getName() + ".sendMessage", e);
            }
            return false;
        }
        catch (Exception e) {
            try {
                ConnectionManager.executeUpadte(CLEARING,
                                                new Object[]{from.getLogin(), text, startTime});
            }
            catch (SQLException e1) {
                SystemManager.getLogger().log(Messenger.class.getName() + ".sendMessage", e);
            }
            return false;
        }
        for (NewMessageListener listener : listeners) {
            if (success.contains(listener))
                listener.newMessage(text, from);
        }
        return true;
    }

    public static synchronized void addListener(NewMessageListener listener) {
        listeners.add(listener);
    }

    public static synchronized void removeListener(NewMessageListener listener) {
        listeners.remove(listener);
    }

    public static synchronized Message[] getInMessages(UserAccaunt accaunt) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_IN_MESSAGES,
                                                          new Object[]{accaunt.getId()},
                                                          handler);
            Message[] mres = new Message[res.length];
            new ArrayUtils<Message>().castArray(mres, res);
            ConnectionManager.executeUpadte(VIEW_MESSAGES,
                                            new Object[]{new Date(), accaunt.getId()});
            return mres;
        } catch (SQLException e) {
            SystemManager.getLogger().log(Messenger.class.getName() + ".getUserMessages", e);
        }
        return null;
    }

    public static synchronized Message[] getOutMessages(UserAccaunt accaunt) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_OUT_MESSAGES,
                                                          new Object[]{accaunt.getId()},
                                                          handler);
            Message[] mres = new Message[res.length];
            new ArrayUtils<Message>().castArray(mres, res);
            return mres;
        } catch (SQLException e) {
            SystemManager.getLogger().log(Messenger.class.getName() + ".getUserMessages", e);
        }
        return null;
    }

    public static synchronized int getUserNewMessages(UserAccaunt accaunt) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_NEW_MESSAGES,
                                                          new Object[]{accaunt.getId()},
                                                          CommonsHandlers.INTEGER_ROW_HANDLER_COUNT);
            return ((Integer) res[0]).intValue();
        } catch (SQLException e) {
            SystemManager.getLogger().log(Messenger.class.getName() + ".getUserNewMessages", e);
        }
        return -1;
    }

    public static synchronized Message getMessage(long hook) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_MESSAGE,
                                                          new Object[]{new Long(hook)},
                                                          handler);
            if (res.length == 0)
                return null;
            ConnectionManager.executeUpadte(READ_MESSAGE, new Object[]{new Date(), new Long(hook)});
            return (Message) res[0];
        } catch (SQLException e) {
            SystemManager.getLogger().log(Messenger.class.getName() + ".getMessage", e);
            return null;
        }
    }

    public static int deleteMessage(int mesId) {
        try {
            return ConnectionManager.executeUpadte(DELETE_MESSAGE, new Object[]{new Integer(mesId)});
        } catch (SQLException e) {
            SystemManager.getLogger().log(Messenger.class.getName()+".deleteMessage",e);
            return 0;
        }
    }
}
