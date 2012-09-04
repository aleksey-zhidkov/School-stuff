package ru.lexx.acsystem.webinterface.phandlers.user;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.jdev.constants.HtmlConstants;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.HtmlFormInputElement;
import ru.jdev.html.forms.elements.HtmlLabel;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 28.01.2006
 * Time: 14:20:38
 */
public class UpdateUserPageHandler implements PageHandler {

    private static final String CHECK_EMAIL =
            "  var re = /^\\\\w+@\\\\w+\\\\.\\\\w{2,3}\\$/;" +
            "  if (re.exec(self.document.@form@.@name@.data) || self.document.@form@.@name@.data.length == 0)" +
            "    return \"true\";" +
            "  else" +
            "    return \"false\";";

    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext rcontext) {
        SimpleRequestContext context = (SimpleRequestContext) rcontext;
        UserAccaunt u_accaunt = (UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME);
        FormImpl form = prepareForm(u_accaunt);
        context.setObject("form", form);
        String old_pass = context.getString("old_pass");
        if (old_pass != null) {
            UserAccaunt accaunt = UserManager.getAccaunt(u_accaunt.getLogin(), old_pass);
            if (accaunt == UserManager.noneAccaunt) {
                context.setFlag("invalid_pass");
                return;
            }
            String pass1 = context.getString("pass1").trim();
            String pass2 = context.getString("pass2").trim();
            String email = context.getString("email").trim();
            ((HtmlFormInputElement) form.getElementByName("email")).setValue(email);
            if (pass1.length() == 0 && pass2.length() == 0) {
                pass1 = old_pass;
            } else if (!pass1.equals(pass2)) {
                pass1 = old_pass;
                context.setFlag("invalid_new_pass");
                context.setBoolean("success", false);
            } else {
                context.setFlag("pass_updated");
            }

            if (email.length() == 0 || email.equalsIgnoreCase(u_accaunt.getEmail())) {
                email = accaunt.getEmail();
            } else if (!Pattern.matches("^[\\p{Alnum}]+@[\\p{Alnum}]+\\.[\\p{Alnum}]{2,3}$", email)) {
                email = accaunt.getEmail();
                context.setFlag("invalid_email");
                context.setBoolean("success", false);
            } else {
                context.setFlag("email_updated");
            }

            if (email.equals(accaunt.getEmail()) && pass1.equals(old_pass) && !context.hasParameter("success")) {
                context.setFlag("no_updated");
                context.setBoolean("success", false);
                return;
            }

            int res = UserManager.upadteAccaunt(accaunt.getLogin(), pass1, accaunt.getFio(), accaunt.getSys_group().getId(), email, accaunt.getLogin());
            context.setBoolean("success", res > 0);
        }
    }

    private static FormImpl prepareForm(UserAccaunt accaunt) {
        String formName = "userUpdateForm";
        FormImpl form = new FormImpl("acsform");
        form.setName(formName);

        TextElement old_pass = new TextElement("old_pass", "", "Пароль");
        old_pass.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        old_pass.setErrorMessage("Введите ваш пароль");
        old_pass.setFormName(formName);

        TextElement pass1 = new TextElement("pass1", "", "Новый пароль");
        pass1.setCheckJs("return new String(self.document." + formName + ".pass1.data == self.document." + formName + ".pass2.data);");
        pass1.setErrorMessage("Пароли не совпадают");
        pass1.setType("password");

        TextElement pass2 = new TextElement("pass2", "", "Повторите новый пароль");
        pass2.setCheckJs("return \"true\";");
        pass2.setType("password");

        TextElement email = new TextElement("email", accaunt.getEmail(), "Электронная почта");
        email.setCheckJs(CHECK_EMAIL);
        email.setErrorMessage("Введите корректеый ящик. Например: myname@myprovider.com!");

        form.addElement(new HtmlLabel(accaunt.getLogin(), "Логин:"));
        form.addElement(new HtmlLabel(accaunt.getFio(), "ФИО:"));
        form.addElement(new HtmlLabel(accaunt.getSys_group().getName(), "Группа:"));
        form.addElement(new HtmlLabel(accaunt.getRegDate().toString(), "Дата регистрации:"));
        form.addElement(old_pass);
        form.addElement(pass1);
        form.addElement(pass2);
        form.addElement(email);
        form.addElement(new SubmitElement("sn", "Обновить"));
        return form;
    }

    public void destroy() {
    }
}
