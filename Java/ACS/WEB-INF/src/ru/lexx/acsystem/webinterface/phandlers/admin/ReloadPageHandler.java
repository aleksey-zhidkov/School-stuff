package ru.lexx.acsystem.webinterface.phandlers.admin;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.forward.Forward;
import ru.jdev.requesthandling.forward.RedirectJSPForward;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 23.02.2006
 * Time: 0:59:18
 */
public class ReloadPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public Forward handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        SystemManager.reload();
        ((ACSRequestContext) context).addMessage(new StatusMessage("Система перезагруженна", StatusMessageType.OK_MESSAGE));

        return new RedirectJSPForward(request.getQueryString());
    }

    public void destroy() {
    }
}
