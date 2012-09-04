package ru.lexx.acsystem.webinterface;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.handler.PageHandlerFactory;
import ru.jdev.utils.logging.LogMessageLevel;
import ru.lexx.acsystem.backend.system.SystemManager;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 24.11.2005
 * Time: 23:24:57
 */
public class ACSPageHandlerFactory implements PageHandlerFactory {
    Map<String, PageHandler> handlers = new HashMap<String, PageHandler>();

    public void filterIniting(Map map) {
        Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            try {
                handlers.put(key, Class.forName((String) map.get(key)).asSubclass(PageHandler.class).newInstance());
            }
            catch (ClassNotFoundException e) {
                SystemManager.getLogger().log(getClass().getName() + ".filterIniting", e);
            }
            catch (IllegalAccessException e) {
                SystemManager.getLogger().log(getClass().getName() + ".filterIniting", e);
            }
            catch (InstantiationException e) {
                SystemManager.getLogger().log(getClass().getName() + ".filterIniting", e);
            }
        }
    }

    public PageHandler buildHandler(HttpServletRequest request) {
        SystemManager.getLogger().log(request.getParameter("page") + " are requested", LogMessageLevel.LEVEL_DEBUG, getClass().getName() + ".buildHandler");
        PageHandler h = handlers.get(request.getParameter("page"));
        SystemManager.getLogger().log(h + " will process a request", LogMessageLevel.LEVEL_DEBUG, getClass().getName() + ".buildHandler");
        return handlers.get(request.getParameter("page"));
    }


    public void filterDestroying
            () {
    }

    public void destroyHandler
            (PageHandler
                    pageHandler) {
    }
}
