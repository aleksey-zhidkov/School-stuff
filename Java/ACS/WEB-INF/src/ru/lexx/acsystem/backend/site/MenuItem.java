package ru.lexx.acsystem.backend.site;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.user.RightsManager;
import ru.lexx.acsystem.backend.user.RightsType;
import ru.jdev.constants.HtmlConstants;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.HtmlFormInputElement;
import ru.jdev.html.forms.elements.SelectElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 17.11.2005
 * Time: 15:40:46
 */
public class MenuItem implements ACSUIEditable {
    private long hook = 0;
    private String name = "";
    private String url = "";
    private int order = 0;
    private RightsType rights;

    MenuItem(String _name, String _url, int _order, RightsType _rigths, long _hook) {
        name = _name;
        url = _url;
        order = _order;
        rights = _rigths;
        hook = _hook;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getOrder() {
        return order;
    }

    public RightsType getRights() {
        return rights;
    }

    public long getHook() {
        return hook;
    }

    public String toString() {
        return "[" + rights + "] " + name + " - " + url;
    }

    void setName(String _name) {
        name = _name;
    }

    void setUrl(String _url) {
        url = _url;
    }

    void setRights(RightsType _rights) {
        rights = _rights;
    }

    void setOrder(int _order) {
        order = _order;
    }

    public String getLabel() {
        return name;
    }

    public int getId() {
        return (int) hook;
    }

    public FormImpl getForm(String type, SimpleRequestContext src) {
        boolean isInsert = ACSUIEditable.FORM_TYPE_INSERT.equalsIgnoreCase(type);
        FormImpl f = new FormImpl("acsform");
        f.setMethod("POST");
        f.setName("menu_form");
        ((FormImpl) f).setFormUserName("Форма " + (isInsert ? " добавления" : " обновления") + " пункта меню навиации");

        TextElement name = new TextElement("name", "", "Имя");

        TextElement url = new TextElement("url", "", "URL");
        url.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        url.setErrorMessage("Не указанн URL");

        SelectElement rights = new SelectElement(RightsManager.getOptinos(), "security", "Необходимые права", "");
        rights.setCheckJs(HtmlConstants.CHECK_SELECTED);
        rights.setErrorMessage("Выбирите права!");
        rights.setSize(5);

        TextElement order = new TextElement("order", "", "Порядок");
        order.setCheckJs(HtmlConstants.CHECK_NUMBER);
        order.setErrorMessage("Не корректный порядок");

        f.addElement(name);
        f.addElement(url);
        f.addElement(rights);
        f.addElement(order);
        f.addElement(new SubmitElement("submit", isInsert ? "Добавить" : "Обновить"));

        return f;
    }

    public void fillForm(FormImpl form, String type) {
        ((HtmlFormInputElement) form.getElementByName("name")).setValue(name == null ? "" : name);
        ((HtmlFormInputElement) form.getElementByName("url")).setValue(url);
        ((HtmlFormInputElement) form.getElementByName("security")).setValue(rights.toString());
        ((HtmlFormInputElement) form.getElementByName("order")).setValue(order + "");
    }

    public FormImpl getFilledForm(String type) {
        FormImpl f = getForm(type, null);
        fillForm(f, type);
        return f;
    }

    public String getEditableName1() {
        return "Пункт меню";
    }

    public String getEditableName2() {
        return getEditableName1();
    }

    public String[] toStringArray() {
        return new String[]{name, url, rights.toString(), order + ""};
    }
}
