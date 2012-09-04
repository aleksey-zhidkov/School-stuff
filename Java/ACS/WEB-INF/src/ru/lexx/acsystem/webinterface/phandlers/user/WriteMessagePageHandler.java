package ru.lexx.acsystem.webinterface.phandlers.user;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.messenger.Messenger;
import ru.lexx.acsystem.backend.messenger.TechSupportRecipientFactory;
import ru.lexx.acsystem.backend.messenger.UserRecipientFactory;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.jdev.constants.HtmlConstants;
import ru.lexx.acsystem.webinterface.ACSRequestContext;
import ru.jdev.html.forms.FormImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 28.01.2006
 * Time: 23:50:30
 */
public class WriteMessagePageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        SimpleRequestContext src = (SimpleRequestContext) context;
        FormImpl f = prepareForm("toadmin".equalsIgnoreCase(src.getNotNullString("type")));
        src.setObject(ACSConstants.FORM_ATTRIBUTE_NAME, f);
        if (src.getString("message_body") != null) {
            ((HtmlFormInputElement) f.getElementByName("message_body")).setValue((String) src.getObject("message_body"));
            ((HtmlFormInputElement) f.getElementByName("recipient")).setValue((String) src.getObject("recipient"));

                        boolean res = Messenger.sendMessage(src.getString("message_body"),
                                                (UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME),
                                                src.getString("recipient").equalsIgnoreCase(UserManager.TESH_SUPPORT.getLogin()) ?
            new TechSupportRecipientFactory() : new UserRecipientFactory(src.getString("recipient")));

            if (res)
                ((ACSRequestContext) context).addMessage(new StatusMessage("Сообщение отправленно", StatusMessageType.OK_MESSAGE));
            else
                ((ACSRequestContext) context).addMessage(new StatusMessage("Сообщение не отправленно", StatusMessageType.ERROR_MESSAGE));
        }
    }

    public void destroy() {
    }

    private FormImpl prepareForm(boolean isToAdmin) {
        FormImpl form = new FormImpl("acsform");
        form.setFormUserName("Форма отправки сообщения");
        form.setName("writemessage");

        form.addElement(getRecipient(isToAdmin));

        TextareaElement tae = new TextareaElement();
        tae.setCols(45);
        tae.setFormName("writemessage");
        tae.setName("message_body");
        tae.setRows(5);
        tae.setTabindex(7);
        tae.setUserName("Сообщение");
        tae.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        tae.setErrorMessage("Введите сообщение");
        form.addElement(tae);

        form.addElement(new SubmitElement("sb", "Отправить"));

        return form;
    }

    private HtmlFormInputElement getRecipient(boolean isToAdmin) {
        if (isToAdmin) {
            TextElement te = new TextElement("recipient", "SUPER_ADMIN", "");
            te.setType("hidden");
            te.setFormName("writemessage");
            return te;
        }
        String[] users = UserManager.getAllUsers();
        SelectElement se = new SelectElement(users, users, "recipient", "Получатель", "");
        se.setFormName("writemessage");
        return se;

    }
}
