package ru.lexx.acsystem.backend.task;

import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.interpretator.common.variable.IVariableManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 15.11.2005
 * Time: 23:25:13
 */
public class TaskRowHandler implements RowHandler {

    static IVariableManager var_manager;

    public Object processRow(ResultSet rs) {
        try {
            List<TaskData> lst = new ArrayList<TaskData>();
            List<Task> tasks = new ArrayList<Task>();
            String forml = rs.getString("formulation");
            String id = rs.getString("id");
            int tpid = rs.getInt("tp.id");
            String tpname = rs.getString("tp.name");
            int tpntr = rs.getInt("tp.need_to_resolve");
            float tpnap = rs.getFloat("tp.need_avg_points");
            int tporder = rs.getInt("tp.part_order");
            int dtr = rs.getInt("days_to_resolve");
            lst.add(new TaskData(TaskData.parseTaskData(var_manager, rs.getString("input")),
                                 TaskData.parseTaskData(var_manager, rs.getString("output")),
                                 rs.getInt("td.id"), Integer.parseInt(id)));
            String level = rs.getString("level");
            while (rs.next()) {
                if (!id.equalsIgnoreCase(rs.getString("id"))) {
                    TaskData[] td = new TaskData[lst.size()];
                    lst.toArray(td);
                    lst.clear();

                    tasks.add(new Task(forml, new TaskPartition(tpid, tpname, tpntr, tpnap, tporder), td, id, dtr, level));
                    forml = rs.getString("formulation");
                    id = rs.getString("id");
                    tpid = rs.getInt("tp.id");
                    tpname = rs.getString("tp.name");
                    tpntr = rs.getInt("tp.need_to_resolve");
                    tpnap = rs.getFloat("tp.need_avg_points");
                    tporder = rs.getInt("tp.part_order");
                    dtr = rs.getInt("days_to_resolve");
                    level = rs.getString("level");
                }
                lst.add(new TaskData(TaskData.parseTaskData(var_manager, rs.getString("input")),
                                     TaskData.parseTaskData(var_manager, rs.getString("output")),
                                     rs.getInt("td.id"), Integer.parseInt(id)));
            }
            TaskData[] td = new TaskData[lst.size()];
            lst.toArray(td);
            lst.clear();
            tasks.add(new Task(forml, new TaskPartition(tpid, tpname, tpntr, tpnap, tporder), td, id, dtr, level));
            return tasks;
        }
        catch (SQLException e) {
            SystemManager.getLogger().log(getClass().getName() + ".processRow", e);
        }
        return null;
    }

    public void setVar_manager(IVariableManager _var_manager) {
        var_manager = _var_manager;
    }
}
