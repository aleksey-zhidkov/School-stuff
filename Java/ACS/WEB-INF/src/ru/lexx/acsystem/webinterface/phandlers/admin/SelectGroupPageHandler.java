package ru.lexx.acsystem.webinterface.phandlers.admin;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.user.SystemGroupsManager;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.SelectElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 21.03.2006
 * Time: 0:37:58
 */
public class SelectGroupPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        FormImpl form = getForm((String) context.getObject("name"), (String) context.getObject("label"));
        form.setAction("index.jsp?admin=true&page=" + context.getObject("fpage"));
        context.setObject(ACSConstants.FORM_ATTRIBUTE_NAME, form);
    }


    public static FormImpl getForm(String name, String label) {
        FormImpl f = new FormImpl("acsform");
        f.setFormUserName("Форма выбора группы");
        SelectElement.Option[] ops = SystemGroupsManager.getGroupOptins();
        SelectElement uid = new SelectElement(ops, name, label, "");

        TextElement tname = new TextElement("name", name, "");
        tname.setType("hidden");
        TextElement tlabel = new TextElement("label", label, "");
        tlabel.setType("hidden");

        SubmitElement sbmt = new SubmitElement("submit", "Смотреть");
        f.addElement(uid);
        f.addElement(sbmt);
        f.addElement(tname);
        f.addElement(tlabel);
        return f;
    }

    public void destroy() {
    }
}
