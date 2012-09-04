package ru.lexx.acsystem.backend.site;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 25.03.2006
 * Time: 0:13:20
 */
public class ActiveSessionsManager {

    private static List<HttpSession> activeSessions = new ArrayList<HttpSession>();

    public static synchronized void addSession(HttpSession session) {
        activeSessions.add(session);
    }

    public static synchronized void removeSession(HttpSession session) {
        activeSessions.remove(session);
    }

    public static synchronized List<HttpSession> getActiveSessions() {
        return activeSessions;
    }
}
