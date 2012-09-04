package ru.lexx.acsystem.backend.site;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 02.06.2006
 * Time: 21:57:34
 */
public class StatusMessage {

    private String message;
    private StatusMessageType type;

    public StatusMessage(String message, StatusMessageType type) {
        this.message =  message;
        this.type = type;
    }

    public String getListItem() {
        StringBuffer res = new StringBuffer();
        res.append("<li style=\"list-style-image:url('./images/");
        res.append(getImageFileName());
        res.append("'); color: ");
        res.append(getColor());
        res.append(";\">");
        res.append(message);

        return res.toString();
    }

    private String getColor() {
        switch (type) {
            case INFO_MESSAGE:
                return "blue";
            case OK_MESSAGE:
                return "green";
            case ERROR_MESSAGE:
                return "red";
        }
        return "";
    }

    private String getImageFileName() {
        switch (type) {
            case INFO_MESSAGE:
                return "i.gif";
            case OK_MESSAGE:
                return "ok-icon.gif";
            case ERROR_MESSAGE:
                return "e-icon.gif";
        }
        return "";
    }

}
