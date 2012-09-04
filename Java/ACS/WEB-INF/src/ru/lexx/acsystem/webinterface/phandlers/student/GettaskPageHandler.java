package ru.lexx.acsystem.webinterface.phandlers.student;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.events.EventManager;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.task.GivenTasksManager;
import ru.lexx.acsystem.backend.task.Task;
import ru.lexx.acsystem.backend.task.TaskManager;
import ru.lexx.acsystem.backend.task.TaskPartitionsManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.interpretator.common.ProgLangEquipmentBuilder;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 12.10.2005
 * Time: 17:18:04
 * To change this template use File | Settings | File Templates.
 */
public class GettaskPageHandler implements PageHandler {

    public void init(Map map) {
    }

    private void giveAutomate(SimpleRequestContext src, UserAccaunt accaunt, HttpServletResponse response) {
        Task t = GivenTasksManager.getUserTask(accaunt);
        Date gdate;
        if (t == null) {
            t = TaskManager.getTask(accaunt);
            if (t == null) {
                ((ACSRequestContext) src).addMessage(new StatusMessage("На данный момент мы не можем предложить вам нового задания", StatusMessageType.INFO_MESSAGE));
                return;
            }
            GivenTasksManager.giveTask(t.getId() + "", accaunt);
            gdate = new Date();
            Map<String, String> params = new HashMap<String, String>();
            params.put("username", accaunt.getLogin());
            params.put("task_id", t.getId() + "");
            EventManager.addEvent("events/user_get_task", params, accaunt.getId(), new Date());
            ((ACSRequestContext) src).addMessage(new StatusMessage("Задание выданно", StatusMessageType.OK_MESSAGE));
        } else {
            gdate = GivenTasksManager.getGivenDate(accaunt);
            ((ACSRequestContext) src).addMessage(new StatusMessage("У вас уже есть задание", StatusMessageType.INFO_MESSAGE));
        }
        src.setObject("task", t);

        Calendar cal = Calendar.getInstance();
        cal.setTime(gdate);
        cal.add(Calendar.DAY_OF_MONTH, t.getDays_to_resolve());
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        src.setObject("need_resolve_to", df.format(cal.getTime()));
    }

    private static void giveManual(SimpleRequestContext src, UserAccaunt accaunt, HttpServletResponse response) {
        Task t = GivenTasksManager.getUserTask(accaunt);
        boolean newTask = false;
        if (t == null) {
            if (src.hasParameter("tid")) {
                t = TaskManager.getTask(src.getString("tid"), ProgLangEquipmentBuilder.getInstance(accaunt.getSys_group().getLang()).buildVariableManager());
                newTask = true;
            } else {
                Task[] tasks = TaskManager.getNotResolvedTasks(accaunt);
                src.setObject("tasks", tasks);
            }
        }
        if (t != null) {
            src.setObject("task", t);
            if (newTask)
                GivenTasksManager.giveTask(t.getId() + "", accaunt);
            Date gdate = GivenTasksManager.getGivenDate(accaunt);
            Calendar cal = Calendar.getInstance();
            cal.setTime(gdate);
            cal.add(Calendar.DAY_OF_MONTH, t.getDays_to_resolve());
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
            src.setObject("need_resolve_to", df.format(cal.getTime()));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Задание выданно", StatusMessageType.OK_MESSAGE));
        } else if (src.hasParameter("tid"))
            ((ACSRequestContext) src).addMessage(new StatusMessage("Задание не выданно", StatusMessageType.ERROR_MESSAGE));
        src.setObject("task", t);
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        SimpleRequestContext src = (SimpleRequestContext) context;
        UserAccaunt accaunt = (UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME);
        if (accaunt.getSys_group().getLang() == ProgLanguage.NONE) {
            ((ACSRequestContext) context).addMessage(new StatusMessage("Ваша группа не может получать задания", StatusMessageType.INFO_MESSAGE));
            return;
        }
        /*if (accaunt.getPartition().getId() == TaskPartitionsManager.finished.getId())
            giveManual(src, accaunt, response);
        else
            */giveAutomate(src, accaunt, response);
    }

    public void destroy() {
    }
}
