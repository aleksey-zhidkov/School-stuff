package ru.lexx.acsystem.backend.task.estimation;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.CheckedTasksManager;
import ru.lexx.acsystem.backend.task.Task;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.interpretator.Interpreter;
import ru.lexx.acsystem.interpretator.common.errors.UserErrorException;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 24.05.2006
 * Time: 1:16:04
 */
public class TimeEstimator implements IEstimator {

    public float estimate(Map params) {
        SimpleRequestContext context = (SimpleRequestContext) params;
        int points = 0;
        Task task = (Task) context.getObject("task");
        String prog = context.getString("prog");
        UserAccaunt accaunt = (UserAccaunt) context.getObject("accaunt");
        boolean res;
        Interpreter intr = new Interpreter();
        try {
            res = intr.interpretate(task, prog, accaunt.getSys_group().getLang());
        } catch (Exception e) {
            SystemManager.getLogger().log(getClass().getName() + ".estimate", e);
            res = false;
            context.setHasError(true);
        }
        if (res) {
            Calendar gdate = Calendar.getInstance();
            gdate.setTime((Date) context.getObject("gdate"));
            Calendar cdate = Calendar.getInstance();
            int day = Calendar.DAY_OF_YEAR;
            int dif = cdate.get(day) - gdate.get(day);
            if (dif <= task.getDays_to_resolve())
                    points = 5;
            else if (5 - (dif - task.getDays_to_resolve()) >= 2)
                    points = 5 - (dif - task.getDays_to_resolve());
            else
                points = 2;
            CheckedTasksManager.returnTask(accaunt, task, points);
        } else {
            UserErrorException e = intr.getException();
            if (e != null)
                context.setObject("except", e);
        }
        context.setBoolean("res",res);
        return points;
    }
}
