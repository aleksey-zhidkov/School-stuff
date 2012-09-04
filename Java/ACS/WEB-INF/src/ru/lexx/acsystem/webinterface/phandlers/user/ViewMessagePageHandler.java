package ru.lexx.acsystem.webinterface.phandlers.user;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.messenger.Message;
import ru.lexx.acsystem.backend.messenger.Messenger;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: malinka
 * Date: 05.02.2006
 * Time: 22:28:16
 * To change this template use File | Settings | File Templates.
 */
public class ViewMessagePageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        SimpleRequestContext src = (SimpleRequestContext) context;
        Message m = Messenger.getMessage(src.getInt("mid"));
        if (m == null)
            try {
                response.sendRedirect("/index.jsp?page=errorpage");
            } catch (IOException e) {
                SystemManager.getLogger().log(getClass().getName() + ".handle", e);
            }
        src.setObject("message", m);
    }

    public void destroy() {
    }
}
