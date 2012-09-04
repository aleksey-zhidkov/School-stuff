package ru.lexx.acsystem.webinterface.phandlers.user;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.messenger.Messenger;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.site.ActiveSessionsManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 12.10.2005
 * Time: 14:04:06
 * To change this template use File | Settings | File Templates.
 */
public class LogoffPageHandler implements PageHandler {
    public void init(Map map) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        UserAccaunt a = (UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME);
        Messenger.removeListener(a);
        ActiveSessionsManager.removeSession(request.getSession());
        request.getSession().invalidate();
        request.getSession(true);
        try {
            response.sendRedirect("index.jsp");
        }
        catch (IOException e) {
            context.setHasError(true);
            SystemManager.getLogger().log(getClass().getName() + ".handle", e);
        }
    }

    public void destroy() {
    }
}
