package ru.lexx.acsystem.backend.task.stat;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: pipsi
 * Date: 21.01.2006
 * Time: 11:28:10
 * To change this template use File | Settings | File Templates.
 */
public class RecivedProgram {
    private int id;
    private int user_id;
    private Date recive_date;
    private String task_forml;
    private String program;
    private boolean isOk;

    RecivedProgram(int _id, int _user_id, Date _recive_date, String _task_forml, String _program, boolean _isOk) {
        id = _id;
        user_id = _user_id;
        recive_date = _recive_date;
        task_forml = _task_forml;
        program = _program;
        isOk = _isOk;
    }

    public int getUser() {
        return user_id;
    }

    public Date getRecive_date() {
        return recive_date;
    }

    public String getTask_forml() {
        return task_forml;
    }

    public String getProgram() {
        return program;
    }

    public boolean isOk() {
        return isOk;
    }

    public int getId() {
        return id;
    }
}
