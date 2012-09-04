package ru.lexx.acsystem.webinterface.phandlers.student;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.task.GivenTasksManager;
import ru.lexx.acsystem.backend.task.Task;
import ru.lexx.acsystem.backend.task.TaskManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.interpretator.common.ProgLangEquipmentBuilder;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: pipsi
 * Date: 21.01.2006
 * Time: 10:58:12
 * To change this template use File | Settings | File Templates.
 */
public class ViewtaskPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        SimpleRequestContext src = (SimpleRequestContext) context;
        UserAccaunt accaunt = (UserAccaunt) request.getSession().getAttribute("accaunt");
        if (!accaunt.hasLang()) {
            ((ACSRequestContext) context).addMessage(new StatusMessage("Вы не можете смотреть задния в текущей группе", StatusMessageType.INFO_MESSAGE));
            return;
        }

        Task task;
        if (context.hasParameter("tid")) {
            ProgLangEquipmentBuilder builder = ProgLangEquipmentBuilder.getInstance(accaunt.getSys_group().getLang());
            task = TaskManager.getTask(((ACSRequestContext) context).getString("tid"), builder.buildVariableManager());
        } else
            task = GivenTasksManager.getUserTask(accaunt);
        if (task == null) {
            ((ACSRequestContext) context).addMessage(new StatusMessage("На данный момент вам не выданно ни одного задания", StatusMessageType.INFO_MESSAGE));
            ((ACSRequestContext) context).setFlag("no_task");
            return;
        }
        String forml = task.getFormulation();
        src.setObject("forml", forml);
        Date gd = GivenTasksManager.getGiveDate(accaunt, task);
        Calendar give_date = Calendar.getInstance();
        give_date.setTime(gd);
        Calendar need_date = Calendar.getInstance();
        need_date.set(Calendar.YEAR, give_date.get(Calendar.YEAR));
        need_date.set(Calendar.MONTH, give_date.get(Calendar.MONTH));
        need_date.set(Calendar.DAY_OF_YEAR, give_date.get(Calendar.DAY_OF_YEAR));
        need_date.add(Calendar.DAY_OF_YEAR, task.getDays_to_resolve());
        src.setObject("give_date", give_date);
        src.setObject("need_date", need_date);
    }

    public void destroy() {
    }
}
