package ru.lexx.acsystem.backend.task;

import ru.jdev.utils.db.rowhandlers.RowHandler;
import ru.lexx.acsystem.interpretator.common.variable.IVariableManager;
import ru.lexx.acsystem.interpretator.pascal.PascalVariableManager;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 22.03.2006
 * Time: 22:35:14
 */
public class TaskDataRowHandler implements RowHandler {
    public Object processRow(ResultSet rs) throws SQLException {
        IVariableManager vm = new PascalVariableManager();
        return new TaskData(TaskData.parseTaskData(vm, rs.getString("input")), TaskData.parseTaskData(vm, rs.getString("output")), rs.getInt("id"), rs.getInt("task_id"));
    }
}
