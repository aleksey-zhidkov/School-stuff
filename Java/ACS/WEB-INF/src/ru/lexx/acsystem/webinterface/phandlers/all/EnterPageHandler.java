package ru.lexx.acsystem.webinterface.phandlers.all;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.site.MenuManager;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.site.ActiveSessionsManager;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.jdev.constants.HtmlConstants;
import ru.lexx.acsystem.webinterface.ACSRequestContext;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.HtmlFormInputElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class EnterPageHandler implements PageHandler {

    private static UserAccaunt login(String pass, String login) {
        return UserManager.getAccaunt(login, pass);
    }

    public void init(Map map) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        String login = (String) context.getObject("login");
        FormImpl form;
        form = prepareForm();
        ((FormImpl) form).setFormUserName("Форма входа в систему");
        context.setObject("form", form);
        if (login == null)
            return;
        ((HtmlFormInputElement) form.getElementByName("login")).setValue(login);
        String pass = (String) context.getObject("pass");
        UserAccaunt accaunt;
        if (pass.length() > 0)
            accaunt = login(pass, login);
        else
            accaunt = UserManager.noneAccaunt;
        boolean res = false;
        if (accaunt != UserManager.noneAccaunt) {
            String rp = (String) request.getSession().getAttribute("requested_page");
            if (rp != null) {
                boolean hasAccess = MenuManager.haveAccess(accaunt.getSys_group().getRights(), rp);
                try {
                    if (hasAccess)
                        response.sendRedirect(SystemManager.getProperty("SERVER_URL") + "index.jsp?page=" + rp + request.getSession().getAttribute("other_params"));
                    else
                        response.sendRedirect(SystemManager.getProperty("SERVER_URL") + "index.jsp?page=accessdenied");
                }
                catch (IOException e) {
                    SystemManager.getLogger().log(getClass().getName() + ".handle", e);
                }
                request.getSession().removeAttribute("requested_page");
            }
            res = true;
        }
        context.setObject("success", new Boolean(res));
        String txt = res ? "you_are_enter" : "enter_fail";
        if (res) {
            ((ACSRequestContext) context).addMessage(new StatusMessage(SystemManager.getText(txt), StatusMessageType.OK_MESSAGE));
            ActiveSessionsManager.addSession(request.getSession());
        }
        else
            ((ACSRequestContext) context).addMessage(new StatusMessage(SystemManager.getText(txt), StatusMessageType.ERROR_MESSAGE));
        request.getSession().setAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME, accaunt);
    }

    public void destroy() {
    }

    private FormImpl prepareForm() {
        FormImpl form = new FormImpl("acsform");
        form.setName("enter_form");
        TextElement tlogin = new TextElement("login", "", "Логин: ");
        tlogin.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        tlogin.setErrorMessage(HtmlConstants.LOGIN_FORGOTTEN);

        TextElement tpass = new TextElement("pass", "", "Пароль: ");
        tpass.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        tpass.setErrorMessage("Забыли ввести пароль");
        tpass.setType("password");

        form.addElement(tlogin);
        form.addElement(tpass);
        form.addElement(new SubmitElement("", "Войти"));
        return form;
    }
}
