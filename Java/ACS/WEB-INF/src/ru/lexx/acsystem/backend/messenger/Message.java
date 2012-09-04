package ru.lexx.acsystem.backend.messenger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: malinka
 * Date: 31.01.2006
 * Time: 20:47:39
 * To change this template use File | Settings | File Templates.
 */
public class Message {

    private int hook;
    private String fromLogin;
    private String toLogin;
    private String text;
    private Date sentDate;
    private Date viewDate;
    private Date readDate;
    private String folder;

    public String getFromLogin() {
        return fromLogin;
    }

    public String getToLogin() {
        return toLogin;
    }

    public String getText() {
        return text;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public Date getViewDate() {
        return viewDate;
    }

    public Date getReadDate() {
        return readDate;
    }

    public String getFolder() {
        return folder;
    }

    public Message(String _fromLogin, String _toLogin, String _text, Date _sentDate,
                   Date _viewDate, Date _readDate, String _folder, int _hook) {
        fromLogin = _fromLogin;
        toLogin = _toLogin;
        text = _text;
        sentDate = _sentDate;
        viewDate = _viewDate;
        readDate = _readDate;
        folder = _folder;
        hook = _hook;
    }

    public int getHook() {
        return hook;
    }
}
