package ru.lexx.acsystem.webinterface.phandlers.student;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.CheckedTasksManager;
import ru.lexx.acsystem.backend.task.GivenTasksManager;
import ru.lexx.acsystem.backend.task.stat.GStatUnit;
import ru.lexx.acsystem.backend.task.stat.UserStatistic;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.webinterface.phandlers.admin.VisualUserStatPageHandler;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: pipsi
 * Date: 22.01.2006
 * Time: 19:15:17
 * To change this template use File | Settings | File Templates.
 */
public class ViewstatPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        UserAccaunt accaunt;
        if (context.hasParameter("admin"))
            accaunt = UserManager.getAccaunt(((SimpleRequestContext) context).getInt("uid"));
        else
            accaunt = (UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME);
        UserStatistic stat = CheckedTasksManager.getUserStat(accaunt);
        if (stat == null) {
            try {
                response.sendRedirect("/errorMessage.jsp");
                return;
            } catch (IOException e) {
                SystemManager.getLogger().log(getClass().getName(), e);
            }
        }
        context.setObject("stat", stat);
        SimpleRequestContext src = (SimpleRequestContext) context;
        GStatUnit[] gstat = GivenTasksManager.getUserStat(accaunt.getId());
        src.setObject("stat0", gstat);
        src.setInt("max_prs", VisualUserStatPageHandler.getPrsCount(gstat));
        src.setObject("users", new UserAccaunt[]{accaunt});
        ((ACSRequestContext) context).addMessage(new StatusMessage("Ваша статистика", StatusMessageType.INFO_MESSAGE));
    }

    public void destroy() {
    }
}
