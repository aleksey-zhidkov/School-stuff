package ru.lexx.acsystem.backend.uiediatble;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.html.forms.FormImpl;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 07.05.2006
 * Time: 1:54:19
 */
public interface ACSUIFilter {

    FormImpl getFilterForm();

    ACSUIEditable[] getFilteredEditables(SimpleRequestContext src);

    String getFilterMessage(ACSRequestContext context);
}
