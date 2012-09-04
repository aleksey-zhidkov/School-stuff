package ru.lexx.acsystem.backend.user;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ProgLanguage;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.HtmlFormInputElement;
import ru.jdev.html.forms.elements.SelectElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;
import ru.jdev.constants.HtmlConstants;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 19.02.2006
 * Time: 23:11:41
 */
public class SystemGroup implements ACSUIEditable {

    private int id;
    private Rights rights;
    private String name;
    private ProgLanguage lang;

    public SystemGroup(Rights _rights, String _name, ProgLanguage _lang, int _id) {
        rights = _rights;
        name = _name;
        lang = _lang;
        id = _id;
    }

    public Rights getRights() {
        return rights;
    }

    public String getName() {
        return name;
    }

    public ProgLanguage getLang() {
        return lang;
    }

    public String getLabel() {
        return name;
    }

    public int getId() {
        return id;
    }

    public FormImpl getForm(String type, SimpleRequestContext src) {
        boolean isInsert = ACSUIEditable.FORM_TYPE_INSERT.equalsIgnoreCase(type);
        FormImpl f = new FormImpl("acsform");
        ((FormImpl) f).setFormUserName((isInsert ? "Форма добавления" : "обновления") + " группы");
        f.setMethod("POST");
        f.setName("group_form");

        TextElement name = new TextElement("name", "", "Название");
        name.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        name.setErrorMessage("Забыли указать название");

        SelectElement rights = new SelectElement(RightsManager.getOptinos(), "security", "права", "");
        rights.setMulti(true);
        rights.setSize(5);
        rights.setCheckJs(HtmlConstants.CHECK_SELECTED);
        rights.setErrorMessage("Выбирите хотя бы одну группу прав!");

        SelectElement prog_lang = new SelectElement(new String[]{"None", "Pascal"}, new String[]{"Отсутсвует", "Pascal"}, "prog_lang", "Язык порграммирования", "");

        f.addElement(name);
        f.addElement(rights);
        f.addElement(prog_lang);
        f.addElement(new SubmitElement("submit", isInsert ? "Добавить" : "Обновить"));
        return f;
    }

    public void fillForm(FormImpl form, String type) {
        ((HtmlFormInputElement) form.getElementByName("name")).setValue(name);
        ((HtmlFormInputElement) form.getElementByName("prog_lang")).setValue(lang == null ? "" : lang.getName());
        SelectElement se = (SelectElement) form.getElementByName("security");
        RightsType[] r = rights.getAllFollows();
        for (RightsType rt : r) {
            se.setValue(rt.toString());
        }
    }

    public FormImpl getFilledForm(String type) {
        FormImpl f = getForm(type, null);
        fillForm(f, type);
        return f;
    }

    public String getEditableName1() {
        return "Группа";
    }

    public String getEditableName2() {
        return "Группу";
    }

    public String[] toStringArray() {
        return new String[]{name, rights.toString(), lang == null ? "" : lang.getName()};
    }
}
