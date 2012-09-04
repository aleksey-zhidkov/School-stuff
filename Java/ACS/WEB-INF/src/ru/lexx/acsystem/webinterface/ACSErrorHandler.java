package ru.lexx.acsystem.webinterface;

import ru.jdev.requesthandling.IErrorHandler;
import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.lexx.acsystem.backend.system.SystemManager;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 19.02.2006
 * Time: 21:32:00
 */
public class ACSErrorHandler implements IErrorHandler {

    public void handleError(ServletRequest request, ServletResponse response, RequestContext context, PageHandler handler, Throwable t) throws IOException {
        SystemManager.getLogger().log(handler.getClass().getName(), t);
        if (response instanceof HttpServletResponse)
            ((HttpServletResponse) response).sendRedirect(SystemManager.getProperty("SERVER_URL") + "index.jsp?page=error_message");
    }

    public void filterIniting(Map params) {
    }
}
