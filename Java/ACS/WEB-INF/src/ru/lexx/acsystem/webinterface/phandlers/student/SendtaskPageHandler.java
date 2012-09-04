package ru.lexx.acsystem.webinterface.phandlers.student;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.events.EventManager;
import ru.lexx.acsystem.backend.messenger.Messenger;
import ru.lexx.acsystem.backend.messenger.TechSupportRecipientFactory;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.*;
import ru.lexx.acsystem.backend.task.estimation.EstimationManager;
import ru.lexx.acsystem.backend.task.estimation.IEstimator;
import ru.lexx.acsystem.backend.task.stat.RecivedProgramManager;
import ru.lexx.acsystem.backend.task.stat.UserStatistic;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.lexx.acsystem.backend.utils.ACSStringUtils;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 12.10.2005
 * Time: 18:42:01
 * To change this template use File | Settings | File Templates.
 */
public class SendtaskPageHandler implements PageHandler {

    private static void doCancel(HttpServletRequest request, HttpServletResponse response, SimpleRequestContext context) {
        UserAccaunt ac = (UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME);
        Task t = GivenTasksManager.getUserTask(ac);
        if (CheckedTasksManager.returnTask(ac, t, 2)) {

            context.setBoolean("cancel_success", true);
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", ac.getLogin());
            params.put("task_id", t.getId() + "");
            EventManager.addEvent("events/user_cancel_task", params, ac.getId(), new Date());
            ((ACSRequestContext) context).addMessage(new StatusMessage("Задание отменено", StatusMessageType.OK_MESSAGE));
        } else
            ((ACSRequestContext) context).addMessage(new StatusMessage("Задание отменено", StatusMessageType.ERROR_MESSAGE));
    }

    private static void doCheck(HttpServletRequest request, HttpServletResponse response, SimpleRequestContext context) {
        // todo добавиьт проверку на ProgLanguage.NONE
        UserAccaunt accaunt = (UserAccaunt) context.getObject("accaunt");
        Task task = (Task) context.getObject("task");
        String prog = context.getString("prog");
        if (task == null) {
            ((ACSRequestContext) context).addMessage(new StatusMessage("У вас нет заданий для проверки", StatusMessageType.INFO_MESSAGE));
            return;
        }
        if (prog != null && prog.length() > 0) {
            context.setObject("accaunt", accaunt);
            IEstimator e = EstimationManager.getEstemator("TimeEstimator");
            float points = e.estimate((Map) context);
            if (context.hasParameter("except")) {
                int lineNum = ((UserErrorException) context.getObject("except")).getLine();
                String line = "";
                if (lineNum > 0)
                    line = " в линии " + lineNum;
                ((ACSRequestContext) context).addMessage(new StatusMessage(((UserErrorException) context.getObject("except")).getMessage() + line, StatusMessageType.ERROR_MESSAGE));
            } else
                ((ACSRequestContext) context).addMessage(new StatusMessage("Синтаксических ошибок нет", StatusMessageType.INFO_MESSAGE));
            context.setObject("points", points + "");
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", accaunt.getLogin());
            params.put("task_id", task.getId() + "");
            String tpl = "";
            String isOk;
            if (context.getBoolean("res")) {
                isOk = "1";
                ((ACSRequestContext) context).addMessage(new StatusMessage("Решение верно, ваша оценка: "+ points , StatusMessageType.OK_MESSAGE));
                params.put("points", points + "");
                tpl = "events/user_send_task_right";
                context.removeObject("task");
            } else {
                isOk = "0";
                ((ACSRequestContext) context).addMessage(new StatusMessage("Решение не верно", StatusMessageType.INFO_MESSAGE));
                tpl = "events/user_send_task_wrong";
            }
            EventManager.addEvent(tpl, params, accaunt.getId(), new Date());
            RecivedProgramManager.insertProgram(prog, accaunt, task.getId(), isOk);
        }
    }

    public void init(Map map) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext rcontext) {
        SimpleRequestContext context = (SimpleRequestContext) rcontext;
        String operation = context.getString("operation");
        UserAccaunt accaunt = (UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME);
        Task task = GivenTasksManager.getUserTask(accaunt);
        if (task == null) {
            ((ACSRequestContext) context).addMessage(new StatusMessage("У вас нет заданий для проверки", StatusMessageType.INFO_MESSAGE));
            return;
        }
        context.setObject("accaunt", accaunt);
        context.setObject("task", task);
        TaskPartition before = TaskPartitionsManager.getUserPartition(accaunt);
        if ("cancel".equalsIgnoreCase(operation))
            doCancel(request, response, context);
        else if ("check".equalsIgnoreCase(operation)) {
            context.setObject("gdate", GivenTasksManager.getGivenDate(accaunt));
            doCheck(request, response, context);
        }
        TaskPartition after = TaskPartitionsManager.getUserPartition(accaunt);
        if (before.getId() != after.getId() && after == TaskPartitionsManager.finished) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("user", accaunt.getLogin());
            UserStatistic stat = CheckedTasksManager.getUserStat(accaunt);
            context.setObject("stat", stat);
            params.put("point", Math.round(stat.getAvgPoints()) + "");
            EventManager.addEvent("events/user_finished", params, accaunt.getId(), new Date());
            context.setFlag("finish");
            ((ACSRequestContext) context).addMessage(new StatusMessage("Программа закончена", StatusMessageType.OK_MESSAGE));
            try {
                Messenger.sendMessage(ACSStringUtils.processTemplate("events/user_finished", params), UserManager.SYSTEM, new TechSupportRecipientFactory());
            }
            catch (IOException e) {
                SystemManager.getLogger().log(getClass().getName() + ".handle", e);
            }
        } else if (before.getId() != after.getId()) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("user", accaunt.getLogin());
            params.put("before", before.getName());
            params.put("after", after.getName());
            EventManager.addEvent("events/new_users_part", params, accaunt.getId(), new Date());
            context.setFlag("new_partition");
            ((ACSRequestContext) context).addMessage(new StatusMessage("Раздел закончен", StatusMessageType.OK_MESSAGE));
        }
        accaunt.setPartition(after);
    }

    public void destroy() {
    }
}
