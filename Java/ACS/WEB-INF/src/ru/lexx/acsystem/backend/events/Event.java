package ru.lexx.acsystem.backend.events;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 09.03.2006
 * Time: 2:44:35
 */
public class Event {

    private int id;
    private String description;
    private int sbj_id;
    private Date date;

    public Event(int _id, String _descr, int _sbj_id, Date _date) {
        id = _id;
        description = _descr;
        sbj_id = _sbj_id;
        date = _date;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getSbj_id() {
        return sbj_id;
    }

    public Date getDate() {
        return date;
    }

}
