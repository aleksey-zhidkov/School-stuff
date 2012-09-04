package ru.lexx.acsystem.backend.task;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.jdev.constants.HtmlConstants;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.HtmlFormInputElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 24.02.2006
 * Time: 11:57:17
 */
public class TaskPartition implements ACSUIEditable {

    private int id;
    private String name;
    private int need_to_resolve;
    private float need_avg_points;
    private int order;

    public TaskPartition(int _id, String _name, int _need_to_resolve, float _need_avg_points, int _order) {
        id = _id;
        name = _name;
        need_to_resolve = _need_to_resolve;
        need_avg_points = _need_avg_points;
        order = _order;
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
        f.setMethod("POST");
        f.setName("task_part_form");
        ((FormImpl) f).setFormUserName("Форма " + (isInsert ? " добавления" : " обновления") + " раздела");

        TextElement name = new TextElement("name", "", "Имя:");
        name.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        name.setErrorMessage("Забыли ввести имя!");

        TextElement ntr = new TextElement("ntr", "", "Необходимо решить:");
        ntr.setCheckJs(HtmlConstants.CHECK_NUMBER);
        ntr.setErrorMessage("Некорректно количсетвно заданий которые необходимо решить в данном разделе!\\nКоличество заданий - это целое положительное число");

        TextElement nap = new TextElement("nap", "", "Требуемый средний балл:");
        nap.setCheckJs(HtmlConstants.CHECK_FLOAT);
        nap.setErrorMessage("Некорректный средний балл!\\nСредний балл - это вещественное положительное число");

        TextElement order = new TextElement("order", "", "Порядок: ");
        order.setCheckJs(HtmlConstants.CHECK_NUMBER);
        order.setErrorMessage("Некорректный порядок!\\nПорядок - это целое положительное число");

        f.addElement(name);
        f.addElement(ntr);
        f.addElement(nap);
        f.addElement(order);
        f.addElement(new SubmitElement("submit", ACSUIEditable.FORM_TYPE_INSERT.equalsIgnoreCase(type) ? "Добавить" : "Обновить"));
        return f;
    }

    public void fillForm(FormImpl form, String type) {
        ((HtmlFormInputElement) form.getElementByName("name")).setValue(name);
        ((HtmlFormInputElement) form.getElementByName("ntr")).setValue(need_to_resolve + "");
        ((HtmlFormInputElement) form.getElementByName("nap")).setValue(need_avg_points + "");
        ((HtmlFormInputElement) form.getElementByName("order")).setValue(order + "");
    }

    public FormImpl getFilledForm(String type) {
        FormImpl f = getForm(type, null);
        fillForm(f, type);
        return f;
    }

    public String getEditableName1() {
        return "раздел";
    }

    public String getEditableName2() {
        return getEditableName1();
    }

    public String[] toStringArray() {
        return new String[]{name, need_to_resolve + "", need_avg_points + "", order + ""};
    }

    public String getName() {
        return name;
    }

    public int getNeed_to_resolve() {
        return need_to_resolve;
    }

    public float getNeed_avg_points() {
        return need_avg_points;
    }

    public int getOrder() {
        return order;
    }
}
