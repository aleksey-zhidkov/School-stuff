package ru.lexx.acsystem.backend.task.stat;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 16.03.2006
 * Time: 1:20:01
 */
public class GStatUnit {

    private int id;
    private int task_id;
    private int user_id;
    private Date give_date;
    private String partition;
    private Date check_date;
    private float points;

    public GStatUnit(int _id, int _task_id, int _user_id, Date _give_date,
                     Date _check_date, float _points, String _partition) {
        id = _id;
        task_id = _task_id;
        user_id = _user_id;
        give_date = _give_date;
        check_date = _check_date;
        points = _points;
        partition = _partition;
    }

    public int getId() {
        return id;
    }

    public int getTask_id() {
        return task_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public Date getGive_date() {
        return give_date;
    }

    public Date getCheck_date() {
        return check_date;
    }

    public float getPoints() {
        return points;
    }

    public String getPartition() {
        return partition;
    }
}
