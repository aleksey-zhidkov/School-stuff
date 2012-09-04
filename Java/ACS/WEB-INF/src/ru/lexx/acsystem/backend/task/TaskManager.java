package ru.lexx.acsystem.backend.task;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.IntegerRowHandler;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditor;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.interpretator.common.ProgLangEquipmentBuilder;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;
import ru.lexx.acsystem.interpretator.common.variable.IVariableManager;
import ru.lexx.acsystem.interpretator.pascal.PascalVariableManager;
import ru.lexx.acsystem.test.envoirment.Taskable;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 04.09.2005
 * Time: 21:26:48
 */
public class TaskManager implements ACSUIEditor {

    private static final TaskRowHandler handler = new TaskRowHandler();
    private static final IntegerRowHandler dtrhandler = new IntegerRowHandler("days_to_resolve");

    private static final String SELECT_TASK = "SELECT t.id, tp.*, formulation,days_to_resolve, " +
            "       input,output,t.level, td.id " +
            "FROM tasks t ,tasks_data td, task_partitions tp " +
            "WHERE t.id = ? AND td.task_id = ? AND t.partition = tp.id";

    private static final String SELECT_DAYS_TO_RESOLVE =
            "SELECT days_to_resolve FROM tasks " +
                    "WHERE id = ?";

    private static final String SELECT_MATCHED_TASKS = "SELECT t.id, tp.*, t.formulation, " +
            "       t.days_to_resolve, td.input, td.output, " +
            "       t.level, td.id " +
            "       FROM task_partitions tp, tasks t, tasks_data td " +
            "            WHERE t.id = td.task_id AND tp.id = t.partition  AND " +
            "                  t.level LIKE ? AND " +
            "                  t.id NOT IN ( " +
            "                               SELECT ct.task_id " +
            "                                      FROM checked_tasks ct " +
            "                                           WHERE ct.user_id = ?) AND " +
            "                  t.partition = ?";

    private static final String UPDATE_TASK = "UPDATE tasks SET " +
            "       formulation = ?, days_to_resolve = ?, " +
            "       level = ?, partition = ?" +
            "       WHERE id = ?";

    private static final String UPDATE_TASK_DATA = "UPDATE tasks_data SET input = ?, output = ? " +
            "WHERE task_id = ?";

    private static final String INSERT_TASK = "INSERT INTO tasks (id, formulation, " +
            "                   partition, days_to_resolve, " +
            "                   level) " +
            "            VALUES (?,?,?,?,?)";

    private static final String INSERT_TASK_DATA = "INSERT INTO tasks_data (id, task_id ,input,output) " +
            "            VALUES (?,?,?,?)";

    private static final String DELETE_TASK = "DELETE FROM tasks WHERE id = ?";

    private static final String SELECT_ALL_TASK = "SELECT t.id, tp.*, formulation,days_to_resolve, " +
            "       input,output,t.level, td.id " +
            "FROM tasks t ,tasks_data td, task_partitions tp " +
            "WHERE td.task_id = t.id AND t.partition = tp.id";

    private static final String SELECT_NOT_RESOLVED_TASKS = "SELECT t.id, tp.*, formulation,days_to_resolve, " +
            "       input,output,t.level, td.id " +
            "FROM tasks t ,tasks_data td, task_partitions tp " +
            "WHERE td.task_id = t.id AND t.partition = tp.id AND" +
            "      t.id NOT IN (SELECT task_id FROM given_tasks WHERE user_id = ?)";

    public static synchronized Task getTask(String _task_id, IVariableManager ctr) {
        try {
            handler.setVar_manager(ctr);
            Object[] res = ConnectionManager.executeQuery(SELECT_TASK,
                    new Object[]{_task_id, _task_id},
                    handler);
            if (res.length == 0)
                return null;
            return (Task) ((List) res[0]).get(0);
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(TaskManager.class.getName() + ".getTask", e);
            return null;
        }
    }

    public static synchronized int getDaysToResolve(String id) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_DAYS_TO_RESOLVE,
                    new Object[]{id},
                    dtrhandler);
            if (res.length == 0)
                return -1;
            return ((Integer) res[0]).intValue();
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(TaskManager.class.getName() + ".getDaysToResolve", e);
            return -1;
        }
    }

    /*
     * This is only for test purposes
    */
    public static synchronized void putDataToTestCase(Taskable test, List<IVariable> input, List<IVariable> output) {
        Task t = new Task("", TaskPartitionsManager.finished, new TaskData[]{new TaskData(input, output, 0, 0)}, "", 0, "5-5");
        test.setTask(t);
    }

    public static synchronized Task getTask(UserAccaunt accaunt) {
        try {
            Integer[] results = CheckedTasksManager.getUserLastResults(accaunt);
            String filter;
            if (results.length == 0)
                filter = "5-5";
            else if (results.length == 1)
                filter = results[0].intValue() + "-" + results[0].intValue();
            else
                filter = results[0].intValue() + "-" + getSecond(results[0].intValue(), results[1].intValue());
            handler.setVar_manager(ProgLangEquipmentBuilder.getInstance(accaunt.getSys_group().getLang()).buildVariableManager());
            Object[] res = ConnectionManager.executeQuery(SELECT_MATCHED_TASKS,
                    new Object[]{filter, accaunt.getId(), accaunt.getPartition().getId()},
                    handler);
            if (res.length == 0)
                return null;
            int i = (int) Math.round(Math.random() * (((ArrayList<Task>) res[0]).size() - 1));
            if (((ArrayList<Task>) res[0]).size() == 1)
                i = 0;
            return (Task) ((List) res[0]).get(i);
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(TaskManager.class.getName() + "getTask(UserAccaunt)", e);
        }
        return null;
    }

    /**
     * Определяет второе число в записи уровня задачи.
     * Если одно или оба числа не пренадлежат [2,5], то выбрасывается исключение.
     * ЕДопустимые записи уровня задачи:
     * <ul>
     * <li> 5-5 (оценка не изменилась); 5-4 (оценка ухудшилась);
     * <li> 4-5 (оценка улучшилась); 4-4 (оценка не изменилась); 4-3 (оценка ухудшилась)
     * <li> 3-4; 3-3; 3-2
     * <li> 2-3; 2-2
     * <ul>
     * Например если параметры будут "5 и 2" соответтственнно, то результат будет равен 4,
     * так же как и для пар "4 и 4" и "3 и 5".
     *
     * @param first  оценка за предпоследнее задание
     * @param second оценка за последнее задание
     * @return Определяет второе число в записи уровня задачи
     */
    private static int getSecond(int first, int second) {
        if (first < 2 || first > 5 || second < 2 || second > 5)
            throw new IllegalArgumentException("Illegal numbers: " + first + "-" + second + ". Valid values are [2,5]");
        int res = 0;
        switch (first) {
            case 5:
                if (second == 5)
                    res = second;
                else
                    res = 4;
                break;
            case 4:
                if (second == 4 || second == 5)
                    res = second;
                else
                    res = 3;
                break;
            case 3:
                if (second == 3 || second == 2)
                    res = second;
                else
                    res = 4;
                break;
            case 2:
                if (second == 2)
                    res = second;
                else
                    res = 3;
        }
        return res;
    }

    public ACSUIEditable getEditable(SimpleRequestContext src) {
        return getTask(src.getString("id"), new PascalVariableManager());
    }

    public ACSUIEditable updatetEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1] && cp[2] && cp[3]) {
            Task t = getTask(src.getString("id"), new PascalVariableManager());
            try {
                ConnectionManager.executeUpadte(UPDATE_TASK,
                        new Object[]{src.getString("forml"), src.getInt("dtr"),
                                src.getString("level"), src.getInt("part"),
                                src.getInt("id")});
                ConnectionManager.executeUpadte(UPDATE_TASK_DATA,
                        new Object[]{src.getString("input"), src.getString("output"),
                                src.getInt("id")});
                ((ACSRequestContext) src).addMessage(new StatusMessage("Задание обновлено", StatusMessageType.OK_MESSAGE));
                return t;
            } catch (SQLException e) {
                SystemManager.getLogger().log(getClass().getName() + ".updatetEditable", e);
                return null;
            }
        } else {
            if (!cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не указанна формулировка", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный раздел", StatusMessageType.ERROR_MESSAGE));
            if (!cp[2])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректное количество дней для решения", StatusMessageType.ERROR_MESSAGE));
            if (!cp[3])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный уровень", StatusMessageType.ERROR_MESSAGE));

            ((ACSRequestContext) src).addMessage(new StatusMessage("Задание не обновлено", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public ACSUIEditable insertEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1] && cp[2] && cp[3] && cp[4] && cp[5]) {
            int hook1;
            int hook2;
            try {
                hook1 = SystemHookGenerator.getNextHook();
                hook2 = SystemHookGenerator.getNextHook();
                ConnectionManager.executeUpadte(INSERT_TASK,
                        new Object[]{hook1, src.getString("forml"), src.getInt("part"),
                                src.getInt("dtr"), src.getString("level")});
                ConnectionManager.executeUpadte(INSERT_TASK_DATA,
                        new Object[]{hook2, hook1, src.getString("input"), src.getString("output")});
                ((ACSRequestContext) src).addMessage(new StatusMessage("Задание добавленно", StatusMessageType.OK_MESSAGE));
                return getTask(hook1 + "", new PascalVariableManager());
            } catch (SQLException e) {
                SystemManager.getLogger().log(getClass().getName() + ".insertEditable", e);
                return null;
            } catch (Exception e) {
                SystemManager.getLogger().log(getClass().getName() + ".insertEditable", e);
                return null;
            }
        } else {
            if (!cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не указанна формулировка", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный раздел", StatusMessageType.ERROR_MESSAGE));
            if (!cp[2])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректное количество дней для решения", StatusMessageType.ERROR_MESSAGE));
            if (!cp[3])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный уровень", StatusMessageType.ERROR_MESSAGE));
            if (!cp[4])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректные входные данные. Данные должны соответсвовать формату:\n&lt;имя переменной&gt;([real|integer|string|boolean])=&lt;значение&gt;", StatusMessageType.ERROR_MESSAGE));
            if (!cp[5])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректные выходные данные. Данные должны соответсвовать формату:\n&lt;имя переменной&gt;([real|integer|string|boolean])=&lt;значение&gt;", StatusMessageType.ERROR_MESSAGE));

            ((ACSRequestContext) src).addMessage(new StatusMessage("Задание не добавленно", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }


    public ACSUIEditable deleteEditable(SimpleRequestContext src) {
        try {
            Task t = getTask(src.getString("id"), new PascalVariableManager());
            int res = ConnectionManager.executeUpadte(DELETE_TASK,
                    new Object[]{t.getId()});
            if (res > 0)
                ((ACSRequestContext) src).addMessage(new StatusMessage("Задание удалено", StatusMessageType.OK_MESSAGE));
            else
                ((ACSRequestContext) src).addMessage(new StatusMessage("Задание не удалено", StatusMessageType.ERROR_MESSAGE));
            return t;
        } catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".insertEditable", e);
            ((ACSRequestContext) src).addMessage(new StatusMessage("Задание не удалено по причине внутренней ошибки", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public String[] getColNames() {
        return new String[]{"Ид", "Формулировка", "Раздел", "Дней на решение", "Уровень"};
    }

    public ACSUIEditable[] getEditables(SimpleRequestContext src) {
        return getAllTasks();
    }

    public static synchronized Task[] getAllTasks() {
        try {
            handler.setVar_manager(new PascalVariableManager());
            Object[] res = ConnectionManager.executeQuery(SELECT_ALL_TASK,
                    ACSConstants.ZERO_ARRAY,
                    handler);
            if (res.length == 0)
                return new Task[0];
            Task[] tres = new Task[((List) res[0]).size()];
            ((List<Task>) res[0]).toArray(tres);
            return tres;
        } catch (SQLException e) {
            SystemManager.getLogger().log(TaskManager.class.getName() + ".getAllTasks", e);
        }
        return new Task[0];
    }

    public String getType() {
        return "task";
    }

    public boolean[] checkInputData(ACSRequestContext ctx) {
        boolean[] res = new boolean[6];
        res[0] = ctx.getString("forml").length() > 0;
        res[1] = TaskPartitionsManager.getPartition(Integer.parseInt(ctx.getString("part"))) != null;
        try {
            Integer.parseInt(ctx.getString("dtr"));
            res[2] = true;
        } catch (NumberFormatException e) {
            res[2] = false;
        }
        res[3] = Pattern.matches("^[2345]{1}-[2345]{1}$", ctx.getString("level"));
        if (ctx.getString("input") != null) {
            try {
                TaskData.parseTaskData(new PascalVariableManager(), ctx.getString("input"));
                res[4] = true;
            }
            catch (Exception e) {
                res[4] = false;
            }
        }
        if (ctx.getString("output") != null) {
            try {
                TaskData.parseTaskData(new PascalVariableManager(), ctx.getString("output"));
                res[5] = true;
            }
            catch (Exception e) {
                res[5] = false;
            }
        }
        return res;
    }

    public String getManagmentLabel() {
        return "Управление заданиями";
    }

    public String getAddLabel() {
        return "Добавление задания";
    }

    public String getUpdateLabel() {
        return "Обновление задания";
    }

    public ACSUIEditable getEditable() {
        return new Task("", TaskPartitionsManager.finished, new TaskData[0], "", 0, "");
    }

    public static synchronized Task[] getNotResolvedTasks(UserAccaunt accaunt) {
        try {
            handler.setVar_manager(ProgLangEquipmentBuilder.getInstance(accaunt.getSys_group().getLang()).buildVariableManager());
            Object[] res = ConnectionManager.executeQuery(SELECT_NOT_RESOLVED_TASKS, new Object[]{accaunt.getId()}, handler);
            if (res.length == 0)
                return new Task[0];
            ArrayList<Task> tasks = (ArrayList<Task>) res[0];
            Task[] tres = new Task[tasks.size()];
            tasks.toArray(tres);
            return tres;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(TaskManager.class.getName() + ".getNotResolvedTasks", e);
            return new Task[0];
        }
    }
}

