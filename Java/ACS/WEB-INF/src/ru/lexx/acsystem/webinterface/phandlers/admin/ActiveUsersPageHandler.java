package ru.lexx.acsystem.webinterface.phandlers.admin;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.forward.RedirectJSPForward;
import ru.jdev.requesthandling.forward.Forward;
import ru.lexx.acsystem.backend.site.ActiveSessionsManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 25.03.2006
 * Time: 0:18:18
 */
public class ActiveUsersPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public Forward handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        List<HttpSession> sess = ActiveSessionsManager.getActiveSessions();
        context.setObject("active_sessions", sess);

        return new RedirectJSPForward(request.getQueryString());
    }

    public void destroy() {
    }
}
