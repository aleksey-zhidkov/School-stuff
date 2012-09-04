package ru.lexx.acsystem.webinterface;

import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.RequestContextFactory;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.utils.string.Converter;
import ru.lexx.acsystem.backend.site.MenuManager;
import ru.lexx.acsystem.backend.site.NewsManager;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.user.RightsType;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 24.11.2005
 * Time: 23:32:13
 */
public class ACSRequestContextFactory implements RequestContextFactory {
    public RequestContext buildContext(Map map, HttpServletRequest request) {
        ACSRequestContext context = new ACSRequestContext();
        try {
            putRequetsParams(context, request);
            putMenu(context);
            putNews(context);
            putHead(context, request);
        }
        catch (Exception e) {
            SystemManager.getLogger().log(getClass().getName() + ".buildContext", e);
        }
        return context;
    }

    private static void putRequetsParams(SimpleRequestContext context, HttpServletRequest request) {
        Enumeration en = request.getParameterNames();
        String name;
        while (en.hasMoreElements()) {
            name = (String) en.nextElement();
            // перекодировка всех параметров на случай встречи русских букв
            String[] values = request.getParameterValues(name);
            if (values.length == 1) {
                context.setObject(name, Converter.getUTFString(request.getParameter(name).toCharArray()));
            } else {
                for (int i = 0; i < values.length; i++) {
                    values[i] = Converter.getUTFString(values[i].toCharArray());
                }
                context.setObject(name, values);
            }
        }
    }

    private static void putMenu(SimpleRequestContext context) {
        context.setObject("none_menu", MenuManager.getMenu(RightsType.RIGHTS_NONE));
        context.setObject("reg_menu", MenuManager.getMenu(RightsType.RIGHTS_REGISTERED));
        context.setObject("user_menu", MenuManager.getMenu(RightsType.RIGHTS_USER));
        context.setObject("admin_menu", MenuManager.getMenu(RightsType.RIGHTS_ADMIN));
    }

    private static void putNews(SimpleRequestContext context) {
        context.setObject("news", NewsManager.getLastNews(5));
    }

    private static void putHead(SimpleRequestContext context, HttpServletRequest request) {
        String title = SystemManager.getText(context.getNotNullString("page") + "_page_name");
        if (title == null)
            title = SystemManager.getText("default_page_name");
        context.setObject("title", title);
    }
}
