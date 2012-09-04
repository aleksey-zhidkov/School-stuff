package ru.lexx.acsystem.backend.user;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.messenger.NewMessageListener;
import ru.lexx.acsystem.backend.task.TaskPartition;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.jdev.constants.HtmlConstants;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.HtmlFormInputElement;
import ru.jdev.html.forms.elements.SelectElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;

import java.util.Date;

public class UserAccaunt implements NewMessageListener, ACSUIEditable {

    private String login;
    private String fio;
    private String email;
    private SystemGroup sys_group;
    private int newMessages;
    private int id;
    private Date regDate;

    private TaskPartition partition;

    UserAccaunt(String _login, String _fio, String _email, SystemGroup _sys_group, int _hasNewMessages,
                int _id, Date _regDate) {
        login = _login;
        fio = _fio;
        email = _email;
        sys_group = _sys_group;
        newMessages = _hasNewMessages;
        id = _id;
        regDate = _regDate;
    }

    public String getLogin() {
        return login;
    }

    public String getFio() {
        return fio;
    }

    public String getEmail() {
        return email;
    }

    public boolean hasLang() {
        return sys_group.getLang() != ProgLanguage.NONE;
    }

    public SystemGroup getSys_group() {
        return sys_group;
    }

    public int isNewMessages() {
        return newMessages;
    }

    public void newMessage(String text, UserAccaunt from) {
        newMessages++;
    }

    public void setNewMessages(int nm) {
        newMessages = nm;
    }

    void nullNewMessages() {
        newMessages = 0;
    }

    void nullLogin() {
        login = "";
    }

    void nullFio() {
        fio = "";
    }

    void nullEmail() {
        email = "";
    }

    void nullSys_group() {
        sys_group = null;
    }

    public String getLabel() {
        return login;
    }

    public int getId() {
        return id;
    }

    public FormImpl getForm(String type, SimpleRequestContext src) {
        boolean isInsert = FORM_TYPE_INSERT.equalsIgnoreCase(type);
        FormImpl form = new FormImpl("acsform");
        form.setName("adduser_form");
        ((FormImpl) form).setFormUserName("Форма "+(isInsert ? "добавления" : "обновления")+" пользователя");

        TextElement login = new TextElement("login", "", "Логин");
        login.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        login.setErrorMessage(HtmlConstants.LOGIN_FORGOTTEN);

        TextElement pass1 = new TextElement("pass1", "", "Пароль");
        if (isInsert) {
            pass1.setCheckJs(HtmlConstants.CHECK_PASSWORDS);
            pass1.setErrorMessage(HtmlConstants.INVALID_PASSWORDS);
        }
        pass1.setType("password");

        TextElement pass2 = new TextElement("pass2", "", "Повторите пароль");
        if (isInsert) {
            pass2.setCheckJs("return \"true\";");
            pass2.setErrorMessage("");
        }
        pass2.setType("password");

        TextElement fio = new TextElement("fio", "", "Фамилия Имя Отчество");
        fio.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        fio.setErrorMessage("Забыли ввести ФИО!");

        TextElement email = new TextElement("email", "", "Электронная почта");
        email.setCheckJs(HtmlConstants.CHECK_EMAIL);
        email.setErrorMessage("Введите корректеый ящик. Например: myname@myprovider.com!");
        if (!isInsert) {
            TextElement old_login = new TextElement("old_login", "", "");
            old_login.setType("hidden");
            form.addElement(old_login);
        }

        SelectElement se = new SelectElement(SystemGroupsManager.getGroupOptins(), "group", "Группа", "");
        form.addElement(login);
        form.addElement(pass1);
        form.addElement(pass2);
        form.addElement(fio);
        form.addElement(email);
        form.addElement(se);
        form.addElement(new SubmitElement("submit", isInsert ? "Добавить" : "Обновить"));
        return form;
    }

    public void fillForm(FormImpl form, String type) {
        ((HtmlFormInputElement) form.getElementByName("login")).setValue(login);
        ((HtmlFormInputElement) form.getElementByName("fio")).setValue(fio);
        ((HtmlFormInputElement) form.getElementByName("email")).setValue(email);
        ((HtmlFormInputElement) form.getElementByName("group")).setValue(sys_group.getId() + "");
        if (ACSUIEditable.FORM_TYPE_UPDATE.equalsIgnoreCase(type))
            ((HtmlFormInputElement) form.getElementByName("old_login")).setValue(login);
    }

    public FormImpl getFilledForm(String type) {
        FormImpl f = getForm(type, null);
        fillForm(f, type);
        return f;
    }

    public String getEditableName1() {
        return "Пользователь";
    }

    public String getEditableName2() {
        return "Пользователя";
    }

    public String[] toStringArray() {
        return new String[]{login, fio, email, regDate.toString(), sys_group.getName()};
    }

    public Date getRegDate() {
        return regDate;
    }

    public TaskPartition getPartition() {
        return partition;
    }

    public void setPartition(TaskPartition _partition) {
        partition = _partition;
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UserAccaunt))
            return false;
        UserAccaunt acc = (UserAccaunt) obj;
        if (sys_group.getRights().contains(RightsType.RIGHTS_ADMIN) &&
            acc == UserManager.TESH_SUPPORT)
            return true;
        else
            return id == ((UserAccaunt) obj).getId();
    }
}
