package ru.lexx.acsystem.backend.events;

import ru.jdev.common.ArrayUtils;
import ru.jdev.utils.db.ConnectionManager;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.utils.ACSStringUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 04.03.2006
 * Time: 20:26:14
 */
public class EventManager {

    private static final String INSERT_EVENT = "INSERT INTO events " +
                                               "       (id, subject_id, description, date) " +
                                               "       VALUES (?,?,?,?)";

    private static final String SELECT_EVENTS_BY_SBJ = "SELECT * " +
                                                       "      FROM events " +
                                                       "           WHERE subject_id = ?";

    public static void addEvent(String tpl, Map<String, String> params, long subj_id, Date date) {
        try {
            long event_hook = SystemHookGenerator.getNextHook();
            String descr = ACSStringUtils.processTemplate(tpl, params);
            ConnectionManager.executeUpadte(INSERT_EVENT,
                                            new Object[]{event_hook, subj_id, descr, new Timestamp(date.getTime())});
        } catch (Exception e) {
            SystemManager.getLogger().log(EventManager.class.getName() + ".addEvent", e);
        }
    }

    public static Event[] getEventsBySubject(long sbj_id) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_EVENTS_BY_SBJ, new Object[]{sbj_id}, new EventRowHandler());
            Event[] eres = new Event[res.length];
            ArrayUtils<Event> u = new ArrayUtils<Event>();
            u.castArray(eres, res);
            return eres;
        } catch (SQLException e) {
            SystemManager.getLogger().log(EventManager.class.getName() + ".getEventsBySubject", e);
        }
        return new Event[0];
    }
}
