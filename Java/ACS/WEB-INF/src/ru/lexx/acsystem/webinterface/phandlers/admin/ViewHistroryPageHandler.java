package ru.lexx.acsystem.webinterface.phandlers.admin;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.events.EventManager;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.lexx.acsystem.webinterface.ACSRequestContext;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.SelectElement;
import ru.jdev.html.forms.elements.SubmitElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 09.03.2006
 * Time: 2:43:22
 */
public class ViewHistroryPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        SimpleRequestContext src = (SimpleRequestContext) context;
        if (context.hasParameter("uid")) {
            context.setObject("events", EventManager.getEventsBySubject(src.getInt("uid")));
            UserAccaunt accaunt = UserManager.getAccaunt(src.getInt("uid"));
            ((ACSRequestContext) context).addMessage(new StatusMessage(SystemManager.getText("acs2.admin.history.user") + " " + accaunt.getLogin(), StatusMessageType.INFO_MESSAGE));
            src.setObject("view_accaunt", accaunt);
        }
        context.setObject(ACSConstants.FORM_ATTRIBUTE_NAME, getForm());
    }

    private FormImpl getForm() {
        FormImpl f = new FormImpl("acsform");
        ((FormImpl) f).setFormUserName("Форма выбора пользователя");
        UserAccaunt[] stds = UserManager.getAllAccauns();
        SelectElement uid = new SelectElement(getOptions(stds), "uid", "Пользователь", "");

        SubmitElement sbmt = new SubmitElement("submit", "Смотреть");
        f.addElement(uid);
        f.addElement(sbmt);
        return f;
    }

    private SelectElement.Option[] getOptions(UserAccaunt[] stds) {
        SelectElement.Option[] ops = new SelectElement.Option[stds.length];
        for (int i = 0; i < stds.length; i++) {
            ops[i] = new SelectElement.Option(stds[i].getLogin(), stds[i].getId() + "");
        }
        return ops;
    }

    public void destroy() {
    }
}
