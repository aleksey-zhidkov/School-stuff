package ru.lexx.acsystem.backend.uiediatble;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.html.forms.FormImpl;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 23.02.2006
 * Time: 14:56:30
 */
public interface ACSUIEditable {

    String FORM_TYPE_INSERT = "insert";
    String FORM_TYPE_UPDATE = "update";

    String getLabel();

    int getId();

    FormImpl getForm(String type, SimpleRequestContext src);

    void fillForm(FormImpl form, String type);

    FormImpl getFilledForm(String type);

    String getEditableName1();

    String getEditableName2();

    String[] toStringArray();
}
