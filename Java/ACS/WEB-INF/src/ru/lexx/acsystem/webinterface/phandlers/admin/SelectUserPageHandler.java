package ru.lexx.acsystem.webinterface.phandlers.admin;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.SelectElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 15.03.2006
 * Time: 22:19:12
 */
public class SelectUserPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        FormImpl form = getForm((String) context.getObject("name"), (String) context.getObject("label"));
        form.setAction("index.jsp?admin=true&page=" + context.getObject("fpage"));
        context.setObject(ACSConstants.FORM_ATTRIBUTE_NAME, form);
    }


    public static FormImpl getForm(String name, String label) {
        FormImpl f = new FormImpl("acsform");
        f.setFormUserName("Форма выбора пользователя");
        UserAccaunt[] stds = UserManager.getStudents();
        SelectElement uid = new SelectElement(getOptions(stds), name, label, "");

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

    private static SelectElement.Option[] getOptions(UserAccaunt[] stds) {
        SelectElement.Option[] ops = new SelectElement.Option[stds.length];
        for (int i = 0; i < stds.length; i++) {
            ops[i] = new SelectElement.Option(stds[i].getLogin(), stds[i].getId() + "");
        }
        return ops;
    }

    public void destroy() {
    }
}
