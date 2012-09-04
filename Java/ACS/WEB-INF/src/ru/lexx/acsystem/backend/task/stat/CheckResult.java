package ru.lexx.acsystem.backend.task.stat;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: pipsi
 * Date: 22.01.2006
 * Time: 19:06:20
 * To change this template use File | Settings | File Templates.
 */
public class CheckResult {
    private String taskId;
    private Date checkDate;
    private int mark;

    CheckResult(String _taskId, Date _checkDate, int _mark) {
        taskId = _taskId;
        checkDate = _checkDate;
        mark = _mark;
    }

    public String getTaskId() {
        return taskId;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public int getMark() {
        return mark;
    }
}
