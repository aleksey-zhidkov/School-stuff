package ru.lexx.acsystem.webinterface;

import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.messenger.Messenger;
import ru.lexx.acsystem.backend.site.ActiveSessionsManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 28.01.2006
 * Time: 22:10:44
 */
public class ACSHttpSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        UserAccaunt a = (UserAccaunt) event.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME);
        ActiveSessionsManager.removeSession(event.getSession());
        if (a != UserManager.noneAccaunt)
            Messenger.removeListener(a);
    }
}
