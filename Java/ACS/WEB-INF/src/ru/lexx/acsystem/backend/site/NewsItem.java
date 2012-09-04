package ru.lexx.acsystem.backend.site;

import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.TextElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.HtmlFormInputElement;
import ru.jdev.html.forms.elements.TextareaElement;
import ru.jdev.constants.HtmlConstants;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.utils.string.StringUtils;

import java.util.Date;
import java.sql.Timestamp;

public class NewsItem implements ACSUIEditable,Comparable {
    private int hook;
    private Timestamp date;
    private String text;

    NewsItem(Timestamp _date, String _text, int _hook) {
        date = _date;
        text = _text;
        hook = _hook;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getHook() {
        return hook;
    }

    public String getLabel() {
        return hook+"";
    }

    public int getId() {
        return hook;
    }

    public FormImpl getForm(String type, SimpleRequestContext src) {
        boolean isInsert = ACSUIEditable.FORM_TYPE_INSERT.equalsIgnoreCase(type);
        FormImpl f = new FormImpl("acsform");
        f.setMethod("POST");
        f.setName("news_form");
        ((FormImpl) f).setFormUserName("Форма " + (isInsert ? " добавления" : " обновления") + " новости");

        TextareaElement text = new TextareaElement("text", "", "Содержание: ");
        text.setCols(20);
        text.setRows(7);
        text.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        text.setErrorMessage("Содержание не может быть пустым!");

        TextElement date = new TextElement("date","","Дата: ");
        date.setCheckJs(HtmlConstants.CHECK_DATE);
        date.setErrorMessage("Дата должна соответсвовать формату \\\"дд.мм.гг\\\"");

        f.addElement(text);
        f.addElement(date);
        f.addElement(new SubmitElement("submit", isInsert ? "Добавить" : "Обновить"));

        return f;
    }

    public void fillForm(FormImpl form, String type) {
        ((HtmlFormInputElement) form.getElementByName("text")).setValue(text);
        ((HtmlFormInputElement) form.getElementByName("date")).setValue(StringUtils.getFormattedDate(date));
    }

    public FormImpl getFilledForm(String type) {
        FormImpl f = getForm(type, null);
        fillForm(f,type);
        return f;
    }

    public String getEditableName1() {
        return "Новость";
    }

    public String getEditableName2() {
        return "новость";
    }

    public String[] toStringArray() {
        return new String[]{hook+"", StringUtils.getFormattedDate(date), text};
    }

    public int compareTo(Object o) {
        if (!(o instanceof NewsItem))
            return -1;
        return -date.compareTo(((NewsItem) o).date);
    }
}
