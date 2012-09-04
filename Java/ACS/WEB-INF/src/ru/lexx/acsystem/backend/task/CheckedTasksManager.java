package ru.lexx.acsystem.backend.task;

import ru.jdev.common.ArrayUtils;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.db.rowhandlers.IntegerRowHandler;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.task.stat.CheckResult;
import ru.lexx.acsystem.backend.task.stat.CheckResultRowHandler;
import ru.lexx.acsystem.backend.task.stat.UserStatistic;
import ru.lexx.acsystem.backend.user.UserAccaunt;

import java.sql.SQLException;
import java.sql.Timestamp;

public class CheckedTasksManager {


    private static final CheckResultRowHandler chandler = new CheckResultRowHandler();
    private static final IntegerRowHandler ihandler = new IntegerRowHandler("points");

    private static final String INSERT_TASK = "INSERT INTO checked_tasks (id,task_id,user_id,check_date,points) " +
                                              "            VALUES (?,?,?,?,?)";
    private static final String SELECT_POINTS = "SELECT task_id, check_date,points FROM checked_tasks " +
                                                "WHERE user_id = ?";

    private static final String SELECT_USER_LAST_RESULTS = "SELECT points FROM checked_tasks WHERE " +
                                                           "user_id = ? ORDER BY id desc LIMIT 2";

    public static synchronized boolean returnTask(UserAccaunt accaunt, Task t, int points) {
        Timestamp ct = new Timestamp(System.currentTimeMillis());
        int res;
        try {
            long hook = SystemHookGenerator.getNextHook();
            res = ConnectionManager.executeUpadte(INSERT_TASK, new Object[]{hook, t.getId(), accaunt.getId(), ct, points});
            return res == 1;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(CheckedTasksManager.class.getName() + ".returnTask", e);
            return false;
        } catch (Exception e) {
            SystemManager.getLogger().log(CheckedTasksManager.class.getName() + ".returnTask", e);
            return false;
        }
    }

    public static synchronized UserStatistic getUserStat(UserAccaunt accaunt) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_POINTS, new Object[]{accaunt.getId()}, chandler);
            CheckResult[] sres = new CheckResult[res.length];
            new ArrayUtils<CheckResult>().castArray(sres, res);
            return new UserStatistic(sres);
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(CheckedTasksManager.class.getName() + ".getUserStat", e);
            return null;
        }
    }

    static synchronized Integer[] getUserLastResults(UserAccaunt accaunt) throws SQLException {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_USER_LAST_RESULTS,
                                                          new Object[]{accaunt.getId()}, ihandler);
            Integer[] ires = new Integer[res.length];
            if (res.length == 1) {
                ires[0] = (Integer) res[0];
            } else if (res.length == 2) {
                ires[0] = (Integer) res[1];
                ires[1] = (Integer) res[0];
            }
            return ires;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(CheckedTasksManager.class.getName() + ".getUserLastResults", e);
            throw e;
        }
    }
}