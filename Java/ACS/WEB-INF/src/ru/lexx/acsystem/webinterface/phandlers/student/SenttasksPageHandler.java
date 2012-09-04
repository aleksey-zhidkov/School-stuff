package ru.lexx.acsystem.webinterface.phandlers.student;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.stat.RecivedProgram;
import ru.lexx.acsystem.backend.task.stat.RecivedProgramManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: pipsi
 * Date: 21.01.2006
 * Time: 11:24:50
 * To change this template use File | Settings | File Templates.
 */
public class SenttasksPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        if (context.hasParameter("rid")) {
            showProgram((ACSRequestContext) context);
            return;
        }
        UserAccaunt accaunt;
        if (context.hasParameter("admin")) {
            accaunt = UserManager.getAccaunt(((SimpleRequestContext) context).getInt("uid"));
            context.setObject("user_accaunt", accaunt);
            ((ACSRequestContext) context).addMessage(new StatusMessage("Программы пользователя "+accaunt.getLogin(), StatusMessageType.INFO_MESSAGE));
        } else {
            accaunt = (UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME);
            ((ACSRequestContext) context).addMessage(new StatusMessage("Ваши программы", StatusMessageType.INFO_MESSAGE));
        }
        RecivedProgram[] prs = RecivedProgramManager.getRecivedPrograms(accaunt);
        if (prs == null) {
            try {
                response.sendRedirect("/errorMessage.jsp");
            } catch (IOException e) {
                SystemManager.getLogger().log(getClass().getName(), e);
            }
            return;
        }
        context.setObject("rec_programs", prs);
        if (prs.length ==0)
                if (context.hasParameter("admin")) {
            ((ACSRequestContext) context).addMessage(new StatusMessage("Пользователь "+accaunt.getLogin() + " ещё не отправил ни одного решения", StatusMessageType.INFO_MESSAGE));
        } else {
            ((ACSRequestContext) context).addMessage(new StatusMessage("Вы не отправили ещё ни одного решения", StatusMessageType.INFO_MESSAGE));
        }
    }

    private void showProgram(ACSRequestContext context) {
        RecivedProgram rp = RecivedProgramManager.getRecivedProgramsById(context.getInt("rid"));
        context.setObject("program", rp);
    }

    public void destroy() {
    }
}
