package ru.lexx.acsystem.backend.task;

import ru.jdev.common.ArrayUtils;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditor;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;
import ru.lexx.acsystem.interpretator.pascal.PascalVariableManager;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 22.03.2006
 * Time: 22:31:29
 */
public class TaskDataManager implements ACSUIEditor {

    private static final RowHandler handler = new TaskDataRowHandler();

    private static final String SELECT_DATA = "SELECT * FROM tasks_data " +
            "         WHERE id = ?";

    private static final String SELECT_DATA_BY_TASK = "SELECT * FROM tasks_data " +
            "         WHERE task_id = ?";

    private static final String INSERT_DATA = "INSERT INTO tasks_data (id, task_id, input, output) " +
            "       VALUES (?,?,?,?)";

    private static final String UPDATE_DATA = "UPDATE tasks_data SET input = ?, output = ? WHERE id = ?";

    private static final String DELETE_DATA = "DELETE from tasks_data WHERE id = ?";
    public static final String CHECK_DATA = "^(\\p{Alpha}{1}\\p{Alnum}*\\((integer|real|string|boolean)\\)=.*($|;))+";

    public ACSUIEditable getEditable(SimpleRequestContext src) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_DATA, new Object[]{src.getInt("id")}, handler);
            if (res.length == 0)
                return null;
            return (ACSUIEditable) res[0];
        } catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".getEditable", e);
        }
        return null;
    }

    public ACSUIEditable updatetEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1]) {
            try {
                ConnectionManager.executeUpadte(UPDATE_DATA, new Object[]{src.getString("input"), src.getString("output"), src.getInt("id")});
            } catch (SQLException e) {
                SystemManager.getLogger().log(getClass().getName() + ".updatetEditable", e);
                return null;
            }
            ((ACSRequestContext) src).addMessage(new StatusMessage("Набор данных обновлён", StatusMessageType.OK_MESSAGE));
            return getEditable(src);
        } else {
            if (cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Некорректные входные данные", StatusMessageType.ERROR_MESSAGE));
            if (cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Некорректные выходные данные", StatusMessageType.ERROR_MESSAGE));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Набор данных не обновлён", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public ACSUIEditable insertEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1]) {
            try {
                int id = SystemHookGenerator.getNextHook();
                ConnectionManager.executeUpadte(INSERT_DATA, new Object[]{id, src.getInt("tid"),
                        src.getString("input"), src.getString("output")});
                src.setInt("id", id);
            } catch (SQLException e) {
                SystemManager.getLogger().log(getClass().getName() + ".updatetEditable", e);
                return null;
            } catch (Exception e) {
                SystemManager.getLogger().log(getClass().getName() + ".updatetEditable", e);
                return null;
            }
            ((ACSRequestContext) src).addMessage(new StatusMessage("Набор данных добавлен", StatusMessageType.OK_MESSAGE));
            return getEditable(src);
        } else {
            if (cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Некорректные входные данные. Данные должны соответсвовать формату:\n&lt;имя переменной&gt;([real|integer|string|boolean])=&lt;значение&gt;", StatusMessageType.ERROR_MESSAGE));
            if (cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Некорректные выходные данные. Данные должны соответсвовать формату:\n&lt;имя переменной&gt;([real|integer|string|boolean])=&lt;значение&gt;", StatusMessageType.ERROR_MESSAGE));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Набор данных не обновлён", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public ACSUIEditable deleteEditable(SimpleRequestContext src) {
        ACSUIEditable ae;
        try {
            ae = getEditable(src);
            int res = ConnectionManager.executeUpadte(DELETE_DATA, new Object[]{src.getInt("id")});
            if (res > 0)
                ((ACSRequestContext) src).addMessage(new StatusMessage("Набор данных удалён", StatusMessageType.OK_MESSAGE));
            else
                ((ACSRequestContext) src).addMessage(new StatusMessage("Набор данных не удалён", StatusMessageType.ERROR_MESSAGE));
        } catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".updatetEditable", e);
            ((ACSRequestContext) src).addMessage(new StatusMessage("Набор данных не удалён по причине внутренней ошибки", StatusMessageType.ERROR_MESSAGE));
            return null;
        } catch (Exception e) {
            SystemManager.getLogger().log(getClass().getName() + ".updatetEditable", e);
            ((ACSRequestContext) src).addMessage(new StatusMessage("Набор данных не удалён по причине внутренней ошибки", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
        return ae;
    }

    public String[] getColNames() {
        return new String[]{"Ид", "Входные данные", "Выходные данные"};
    }

    public ACSUIEditable[] getEditables(SimpleRequestContext src) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_DATA_BY_TASK, new Object[]{src.getInt("tid")}, handler);
            TaskData[] ares = new TaskData[res.length];
            ArrayUtils<TaskData> au = new ArrayUtils<TaskData>();
            au.castArray(ares, res);
            return ares;
        } catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".getFilteredEditables", e);
            return new ACSUIEditable[0];
        }
    }

    public String getType() {
        return "task_data";
    }

    public boolean[] checkInputData(ACSRequestContext ctx) {
        boolean[] cp = new boolean[2];
        if (ctx.getString("input") != null) {
            try {
                TaskData.parseTaskData(new PascalVariableManager(), ctx.getString("input"));
                cp[0] = true;
            }
            catch (Exception e) {
                cp[0] = false;
            }
        }
        if (ctx.getString("output") != null) {
            try {
                TaskData.parseTaskData(new PascalVariableManager(), ctx.getString("output"));
                cp[1] = true;
            }
            catch (Exception e) {
                cp[1] = false;
            }
        }
        return cp;
    }

    public String getManagmentLabel() {
        return "Управление наборами данных";
    }

    public String getAddLabel() {
        return "Добавление набора данных";
    }

    public String getUpdateLabel() {
        return "Обновление набора данных";
    }

    public ACSUIEditable getEditable() {
        return new TaskData(new ArrayList<IVariable>(), new ArrayList<IVariable>(), 0, 0);
    }
}