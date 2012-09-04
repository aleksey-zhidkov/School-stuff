package ru.lexx.acsystem.backend.task;

import ru.jdev.common.ArrayUtils;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditor;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.webinterface.ACSRequestContext;
import ru.jdev.html.forms.elements.SelectElement;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 24.02.2006
 * Time: 11:58:46
 */
public class TaskPartitionsManager implements ACSUIEditor {

    private static final RowHandler handler = new TaskPartitionRowHandler();

    public static final TaskPartition finished = getPartition(1000000257);

    private static final String GET_ALL_PARTITIONS = "SELECT id,name,need_to_resolve, need_avg_points, part_order " +
                                                     "       FROM task_partitions " +
                                                     "            WHERE id NOT IN (SELECT id FROM disabled)" +
                                                     "                  ORDER BY part_order";

    private static final String GET_PARTITION_BY_ID = "SELECT id,name,need_to_resolve, need_avg_points, part_order " +
                                                      "       FROM task_partitions " +
                                                      "            WHERE id = ?";

    private static final String UPDATE_PARTITION = "UPDATE task_partitions " +
                                                   "       SET name = ?, need_to_resolve = ?, need_avg_points = ?, " +
                                                   "           part_order = ? " +
                                                   "           WHERE id = ?";

    private static final String INSERT_PARTITION = "INSERT INTO task_partitions (id,name,need_to_resolve," +
                                                   "                             need_avg_points, part_order) " +
                                                   "       VALUES (?,?,?,?,?)";

    private static final String DELETE_PARTITION = "DELETE FROM task_partitions WHERE id = ?";

    private static final String SELECT_ALL_PARTITIONS = "SELECT * FROM task_partitions";

    private static final String SELECT_USER_PARTITION = "SELECT * " +
                                                        "      FROM task_partitions tp " +
                                                        "           HAVING tp.need_to_resolve > ( " +
                                                        "                                        SELECT count(*) " +
                                                        "                                               FROM checked_tasks ct, tasks t" +
                                                        "                                                    WHERE ct.user_id = ? AND " +
                                                        "                                                          ct.task_id = t.id AND " +
                                                        "                                                          t.partition = tp.id " +
                                                        "                                                          ORDER BY part_order desc" +
                                                        "                                       ) OR" +
                                                        "                  tp.need_avg_points > ( " +
                                                        "                                        SELECT avg(points) " +
                                                        "                                               FROM checked_tasks ct, tasks t " +
                                                        "                                                    WHERE ct.user_id = ? AND " +
                                                        "                                                          ct.task_id = t.id AND " +
                                                        "                                                          t.partition = tp.id " +
                                                        "                                                          ORDER BY part_order desc" +
                                                        "                                       ) ORDER BY part_order LIMIT 1";

    public static synchronized TaskPartition[] getAllPartition() {
        try {
            Object[] res = ConnectionManager.executeQuery(GET_ALL_PARTITIONS,
                                                          ACSConstants.ZERO_ARRAY,
                                                          handler);
            TaskPartition[] tres = new TaskPartition[res.length];
            ArrayUtils<TaskPartition> au = new ArrayUtils<TaskPartition>();
            au.castArray(tres, res);
            return tres;
        } catch (SQLException e) {
            SystemManager.getLogger().log(TaskPartitionsManager.class.getName() + ".getAllPartitions", e);
            return new TaskPartition[0];
        }
    }

    public static synchronized TaskPartition getPartition(int id) {
        try {
            Object[] res = ConnectionManager.executeQuery(GET_PARTITION_BY_ID,
                                                          new Object[]{id},
                                                          handler);
            if (res.length == 0)
                return null;
            return (TaskPartition) res[0];
        } catch (SQLException e) {
            SystemManager.getLogger().log(TaskPartitionsManager.class.getName() + ".getPartition", e);
        }
        return null;
    }

    public static SelectElement.Option[] getOptions() {
        TaskPartition[] tps = getAllPartition();
        SelectElement.Option[] ops = new SelectElement.Option[tps.length];
        for (int i = 0; i < tps.length; i++) {
            ops[i] = new SelectElement.Option(tps[i].getName(), tps[i].getId() + "");
        }
        return ops;
    }

    public ACSUIEditable getEditable(SimpleRequestContext src) {
        return getPartition(src.getInt("id"));
    }

    public ACSUIEditable updatetEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1] && cp[2] && cp[3]) {
            TaskPartition tp = getPartition(src.getInt("id"));
            try {
                ConnectionManager.executeUpadte(UPDATE_PARTITION, new Object[]{src.getString("name"),
                                                                               src.getInt("ntr"),
                                                                               src.getFloat("nap"),
                                                                               src.getInt("order"),
                                                                               tp.getId()});
            } catch (SQLException e) {
                SystemManager.getLogger().log(TaskPartitionsManager.class.getName() + ".updateEditable", e);
                tp = null;
            }
            ((ACSRequestContext) src).addMessage(new StatusMessage("Раздел обновлён", StatusMessageType.OK_MESSAGE));
            return tp;
        } else {
            if (cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректное название", StatusMessageType.ERROR_MESSAGE));
            if (cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректное количество необходимых решений", StatusMessageType.ERROR_MESSAGE));
            if (cp[2])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Некорретный необходимый средний балл", StatusMessageType.ERROR_MESSAGE));
            if (cp[3])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный порядок", StatusMessageType.ERROR_MESSAGE));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Раздел не обновлён", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public ACSUIEditable insertEditable(SimpleRequestContext src) {
        boolean[] cp = checkInputData((ACSRequestContext) src);
        if (cp[0] && cp[1] && cp[2] && cp[3]) {
            long id = 0;
            try {
                id = SystemHookGenerator.getNextHook();
                ConnectionManager.executeUpadte(INSERT_PARTITION, new Object[]{id,
                                                                               src.getString("name"),
                                                                               src.getInt("ntr"),
                                                                               src.getFloat("nap"),
                                                                               src.getInt("order")});
            } catch (SQLException e) {
                SystemManager.getLogger().log(TaskPartitionsManager.class.getName() + ".insertEditable", e);
            } catch (Exception e) {
                SystemManager.getLogger().log(TaskPartitionsManager.class.getName() + ".insertEditable", e);
            }
            ((ACSRequestContext) src).addMessage(new StatusMessage("Раздел добавлен", StatusMessageType.OK_MESSAGE));
            return getPartition((int) id);
        } else {
            if (!cp[0])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректное название", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректное количество необходимых решений", StatusMessageType.ERROR_MESSAGE));
            if (!cp[2])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Некорретный необходимый средний балл", StatusMessageType.ERROR_MESSAGE));
            if (!cp[3])
                ((ACSRequestContext) src).addMessage(new StatusMessage("Не корректный порядок", StatusMessageType.ERROR_MESSAGE));
            ((ACSRequestContext) src).addMessage(new StatusMessage("Раздел не добавлен", StatusMessageType.ERROR_MESSAGE));
            return null;
        }
    }

    public ACSUIEditable deleteEditable(SimpleRequestContext src) {
        TaskPartition tp = getPartition(src.getInt("id"));
        try {
            ConnectionManager.executeUpadte(DELETE_PARTITION, new Object[]{tp.getId()});
        } catch (SQLException e) {
            SystemManager.getLogger().log(TaskPartitionsManager.class.getName() + ".deleteEditable", e);
            tp = null;
        }

        return tp;
    }

    public String[] getColNames() {
        return new String[]{"Название", "Кол-во требуемых решений", "Требуемый спедний балл", "Порядок"};
    }

    public ACSUIEditable[] getEditables(SimpleRequestContext src) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_ALL_PARTITIONS, ACSConstants.ZERO_ARRAY, handler);
            TaskPartition[] tres = new TaskPartition[res.length];
            ArrayUtils<TaskPartition> au = new ArrayUtils<TaskPartition>();
            au.castArray(tres, res);
            return tres;
        } catch (SQLException e) {
            SystemManager.getLogger().log(TaskPartitionsManager.class.getName() + ".deleteEditable", e);
        }
        return new ACSUIEditable[0];
    }

    public String getType() {
        return "task_part";
    }

    public boolean[] checkInputData(ACSRequestContext ctx) {
        boolean[] res = {true, true, true, true};
        res[0] = ctx.getString("name").length() > 0;
        try {
            Integer.parseInt(ctx.getString("ntr"));
        } catch (NumberFormatException e) {
            res[1] = false;
        }
        try {
            Float.parseFloat(ctx.getString("nap"));
        }
        catch (NumberFormatException e) {
            res[2] = false;
        }
        try {
            Integer.parseInt(ctx.getString("order"));
        }
        catch (NumberFormatException e) {
            res[3] = false;
        }
        return res;
    }

    public String getManagmentLabel() {
        return "Управление разделами";
    }

    public String getAddLabel() {
        return "Добавление раздела";
    }

    public String getUpdateLabel() {
        return "Обновление раздела";
    }

    public ACSUIEditable getEditable() {
        return new TaskPartition(0, "", 0, 0.0f, -1);
    }

    public static TaskPartition getUserPartition(UserAccaunt accaunt) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_USER_PARTITION,
                                                          new Object[]{accaunt.getId(), accaunt.getId()},
                                                          handler);
            if (res.length == 0)
                return finished;
            return (TaskPartition) res[0];
        } catch (SQLException e) {
            SystemManager.getLogger().log(TaskPartitionsManager.class.getName() + ".getUserPartition", e);
            return null;
        }
    }
}
