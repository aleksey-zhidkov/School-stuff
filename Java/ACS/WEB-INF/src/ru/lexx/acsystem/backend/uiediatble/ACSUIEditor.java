package ru.lexx.acsystem.backend.uiediatble;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 23.02.2006
 * Time: 15:01:33
 */
public interface ACSUIEditor {

    ACSUIEditable getEditable(SimpleRequestContext src);

    ACSUIEditable getEditable();

    ACSUIEditable updatetEditable(SimpleRequestContext src);

    ACSUIEditable insertEditable(SimpleRequestContext src);

    ACSUIEditable deleteEditable(SimpleRequestContext src);

    ACSUIEditable[] getEditables(SimpleRequestContext src);

    String[] getColNames();

    String getType();

    boolean[] checkInputData(ACSRequestContext ctx);

    String getManagmentLabel();

    String getAddLabel();

    String getUpdateLabel();

}