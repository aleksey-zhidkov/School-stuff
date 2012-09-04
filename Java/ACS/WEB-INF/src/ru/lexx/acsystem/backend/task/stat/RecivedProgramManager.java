package ru.lexx.acsystem.backend.task.stat;

import ru.jdev.common.ArrayUtils;
import ru.jdev.utils.db.ConnectionManager;
import ru.lexx.acsystem.backend.system.SystemHookGenerator;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;

import java.sql.SQLException;
import java.sql.Timestamp;

public class RecivedProgramManager {

    private static final RecivedProgramRowHandler handler = new RecivedProgramRowHandler();

    private static final String SELECT_RECIVED_PROGRAMS = "SELECT rp.user_id, rp.recive_date, t.formulation, " +
                                                          "       rp.program, rp.isOk, rp.id " +
                                                          "FROM recived_programs rp, tasks t " +
                                                          "WHERE rp.user_id = ? AND rp.task_id = t.id " +
                                                          "ORDER BY rp.id desc";

    private static final String SELECT_RECIVED_PROGRAM_BY_ID = "SELECT rp.user_id, rp.recive_date, t.formulation, " +
                                                               "       rp.program, rp.isOk, rp.id " +
                                                               "FROM recived_programs rp, tasks t " +
                                                               "WHERE rp.task_id = t.id and rp.id = ? ";

    private static final String INSERT_RECIVED_PROGRAM =
            "INSERT INTO recived_programs (id,program, user_id, recive_date, task_id, isOk) " +
            "VALUES (?,?,?,?,?,?)";

    public static synchronized void insertProgram(String prog, UserAccaunt accaunt, int id, String isOk) {
        try {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            long hook = SystemHookGenerator.getNextHook();
            ConnectionManager.executeUpadte(INSERT_RECIVED_PROGRAM,
                                            new Object[]{hook, prog, accaunt.getId(), ts, id, isOk});
        } catch (SQLException e) {
            SystemManager.getLogger().log(RecivedProgramManager.class.getName() + ".insertProgram", e);
        } catch (Exception e) {
            SystemManager.getLogger().log(RecivedProgramManager.class.getName() + ".insertProgram", e);
        }
    }

    public static synchronized RecivedProgram[] getRecivedPrograms(UserAccaunt accaunt) {
        try {
            Object[] res = ConnectionManager.executeQuery(SELECT_RECIVED_PROGRAMS,
                                                          new Object[]{accaunt.getId()},
                                                          handler);
            RecivedProgram[] rpres = new RecivedProgram[res.length];
            new ArrayUtils<RecivedProgram>().castArray(rpres, res);
            return rpres;
        } catch (SQLException e) {
            SystemManager.getLogger().log(RecivedProgramManager.class.getName(), e);
        }
        return null;
    }

    public static RecivedProgram getRecivedProgramsById(int id) {
            try {
            Object[] res = ConnectionManager.executeQuery(SELECT_RECIVED_PROGRAM_BY_ID,
                                                          new Object[]{new Integer(id)},
                                                          handler);
            if (res.length > 0)
                return (RecivedProgram) res[0];
        } catch (SQLException e) {
            SystemManager.getLogger().log(RecivedProgramManager.class.getName(), e);
        }
        return null;
    }
}
