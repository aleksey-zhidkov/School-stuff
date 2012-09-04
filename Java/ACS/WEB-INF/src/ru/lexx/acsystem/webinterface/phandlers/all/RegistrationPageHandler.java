package ru.lexx.acsystem.webinterface.phandlers.all;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.events.EventManager;
import ru.lexx.acsystem.backend.messenger.Messenger;
import ru.lexx.acsystem.backend.messenger.TechSupportRecipientFactory;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.user.SystemGroupsManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.lexx.acsystem.backend.utils.ACSStringUtils;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.jdev.constants.HtmlConstants;
import ru.lexx.acsystem.webinterface.ACSRequestContext;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.HtmlFormInputElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistrationPageHandler implements PageHandler {

    public void init(Map map) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext rcontext) {

        SimpleRequestContext context = (SimpleRequestContext) rcontext;
        FormImpl form = prepareForm();
        context.setObject("form", form);
        if (context.hasParameter("login")) {
            // getting request parameters
            String login = context.getString("login").trim();
            String pass1 = context.getString("pass1").trim();
            String pass2 = context.getString("pass2").trim();
            String email = context.getString("email").trim();
            String fio = context.getString("fio").trim();
            ((HtmlFormInputElement) form.getElementByName("login")).setValue(login);
            ((HtmlFormInputElement) form.getElementByName("fio")).setValue(fio);
            ((HtmlFormInputElement) form.getElementByName("email")).setValue(email);

            boolean[] cp = new UserManager().checkInputData((ACSRequestContext) context);

            // putting results to context
            ACSRequestContext ctx = (ACSRequestContext) context;
            if (!cp[0])
                ctx.addMessage(new StatusMessage("Длина логина должны быть больше 0.", StatusMessageType.ERROR_MESSAGE));
            if (!cp[1])
                ctx.addMessage(new StatusMessage("Длина пароля должна быть больше 0 и он должен совпадать с подтверждением.", StatusMessageType.ERROR_MESSAGE));
            if (!cp[2])
                ctx.addMessage(new StatusMessage("Некорректный электронный адрес.", StatusMessageType.ERROR_MESSAGE));
            if (!cp[3])
                ctx.addMessage(new StatusMessage("Забыли указать ФИО.", StatusMessageType.ERROR_MESSAGE));
            if (!cp[4])
                ctx.addMessage(new StatusMessage("Пользователь с данным логином уже существует.", StatusMessageType.ERROR_MESSAGE));
            UserAccaunt accaunt = null;
            if (cp[0] && cp[1] && cp[2] && cp[3] && cp[4]) {
                accaunt = UserManager.createAccaunt(login, pass1, fio, email,
                                                    SystemGroupsManager.DEFAULT_GROUP);
            }
            context.setBoolean("succsess", accaunt != null);
            if (accaunt != null)
                ctx.addMessage(new StatusMessage("Регистрация прошла успешно!", StatusMessageType.OK_MESSAGE));
            else
                ctx.addMessage(new StatusMessage("Регистрация не удалась.", StatusMessageType.ERROR_MESSAGE));
            // putting new user accaunt to session
            if (accaunt != null) {
                request.getSession().setAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME, accaunt);
                context.setObject("img", "images/namebegin_success.gif");
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", accaunt.getLogin());
                EventManager.addEvent("events/user_registered", params, accaunt.getId(), new Date());
                try {
                    Messenger.sendMessage(ACSStringUtils.processTemplate("events/user_registered", params), UserManager.SYSTEM, new TechSupportRecipientFactory());
                }
                catch (IOException e) {
                    SystemManager.getLogger().log(getClass().getName() + ".handle", e);
                }
            } else {
                context.setObject("img", "images/namebegin_fail.gif");
            }
        }
    }

    public void destroy() {
    }

    private static FormImpl prepareForm() {
        FormImpl form = new FormImpl("acsform");
        form.setName("regform");
        ((FormImpl) form).setFormUserName("Форма регистрации");

        TextElement login = new TextElement("login", "", "Логин: ");
        login.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        login.setErrorMessage(HtmlConstants.LOGIN_FORGOTTEN);

        TextElement pass1 = new TextElement("pass1", "", "Пароль: ");
        pass1.setCheckJs(HtmlConstants.CHECK_PASSWORDS);
        pass1.setErrorMessage(HtmlConstants.INVALID_PASSWORDS);
        pass1.setType("password");

        TextElement pass2 = new TextElement("pass2", "", "Подтвердите пароль: ");
        pass2.setCheckJs("return \"true\";");
        pass2.setErrorMessage("");
        pass2.setType("password");

        TextElement fio = new TextElement("fio", "", "Фамилия Имя Отчество: ");
        fio.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        fio.setErrorMessage("Забыли ввести ФИО!");

        TextElement email = new TextElement("email", "", "Электронная почта: ");
        email.setCheckJs(HtmlConstants.CHECK_EMAIL);
        email.setErrorMessage("Введите корректеый ящик. Например: myname@myprovider.com!");

        form.addElement(login);
        form.addElement(pass1);
        form.addElement(pass2);
        form.addElement(fio);
        form.addElement(email);
        form.addElement(new SubmitElement("", "Зарегистрироваться"));
        return form;
    }
}
