package ru.lexx.acsystem.backend.task;

import ru.jdev.common.ArrayUtils;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.DateRowHandler;
import ru.lexx.acsystem.backend.constants.CommonsHandlers;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.stat.GStatUnit;
import ru.lexx.acsystem.backend.task.stat.GStatUnitRowHandler;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.interpretator.common.ProgLangEquipmentBuilder;
import ru.lexx.acsystem.interpretator.common.variable.IVariableManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class GivenTasksManager {

    private static final DateRowHandler dhandler = new DateRowHandler("give_date");

    private static final String INSERT_TASK = "INSERT INTO given_tasks (id,task_id,user_id,give_date)" +
                                              " VALUES (?,?,?,?)";

    private static final String SELECT_USER_TASK = "SELECT task_id FROM given_tasks" +
                                                   "  WHERE task_id NOT IN (" +
                                                   "                  SELECT task_id FROM checked_tasks" +
                                                   "                  WHERE user_id = ?" +
                                                   "                )" +
                                                   "  AND user_id = ?";

    private static final String SELECT_GIVE_DATE = "SELECT give_date FROM given_tasks" +
                                                   "  WHERE task_id NOT IN (" +
                                                   "                  SELECT task_id FROM checked_tasks" +
                                                   "                  WHERE user_id = ?" +
                                                   "                )" +
                                                   "  AND user_id = ?";

    private static final String SELECT_GIVE_DATE_UT = "SELECT give_date FROM given_tasks" +
                                                   "  WHERE user_id = ? AND task_id = ?";

    private static final String SELECT_STAT = "SELECT g.id, g.task_id, g.user_id, g.give_date, c.points, c.check_date, tp.name " +
                                              "       FROM given_tasks g LEFT OUTER JOIN checked_tasks c ON g.task_id = c.task_id AND g.user_id = c.user_id, " +
                                              "            tasks t, task_partitions tp " +
                                              "            WHERE g.user_id = ? AND g.task_id = t.id AND t.partition = tp.id " +
                                              "                  ORDER BY tp.part_order, g.give_date";

    public static synchronized int giveTask(String tid, UserAccaunt accaunt) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        int res;
        try {
            long hook = SystemHookGenerator.getNextHook();
            res = ConnectionManager.executeUpadte(INSERT_TASK, new Object[]{hook, tid, accaunt.getId(), ts});
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(GivenTasksManager.class.getName() + ".giveTask", e);
            res = 0;
        } catch (Exception e) {
            SystemManager.getLogger().log(GivenTasksManager.class.getName() + ".giveTask", e);
            res = 0;
        }
        return res;
    }

    public static synchronized Task getUserTask(UserAccaunt accaunt) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_USER_TASK,
                                                          new Object[]{accaunt.getId(), accaunt.getId()},
                                                          CommonsHandlers.STRING_ROW_HANDLER_TASK_ID);
            if (res.length == 0)
                return null;
            IVariableManager ctr = ProgLangEquipmentBuilder.getInstance(accaunt.getSys_group().getLang()).buildVariableManager();
            return TaskManager.getTask((String) res[0], ctr);
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(GivenTasksManager.class.getClass().getName() + ".getUserTask", e);
        }
        return null;
    }

    public static synchronized Date getGivenDate(UserAccaunt accaunt) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_GIVE_DATE,
                                                          new Object[]{accaunt.getId(), accaunt.getId()},
                                                          dhandler);
            if (res.length == 0)
                return null;
            return (Date) res[0];
        } catch (SQLException e) {
            SystemManager.getLogger().log(GivenTasksManager.class.getName() + ".getGivenDate", e);
            return null;
        }
    }

    public static synchronized Date getGiveDate(UserAccaunt accaunt, Task task) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_GIVE_DATE_UT,
                                                          new Object[]{accaunt.getId(), task.getId()},
                                                          dhandler);
            if (res.length == 0)
                return null;
            return (Date) res[0];
        } catch (SQLException e) {
            SystemManager.getLogger().log(GivenTasksManager.class.getName() + ".getGivenDate", e);
            return null;
        }
    }

    public static synchronized GStatUnit[] getUserStat(int user_id) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_STAT, new Object[]{user_id}, new GStatUnitRowHandler());
            ArrayUtils<GStatUnit> au = new ArrayUtils<GStatUnit>();
            GStatUnit[] gres = new GStatUnit[res.length];
            au.castArray(gres, res);
            return gres;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(GivenTasksManager.class.getClass().getName() + ".getUserStat", e);
            return new GStatUnit[0];
        }
    }
}
