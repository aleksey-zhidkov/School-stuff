package ru.lexx.acsystem.backend.task;

import ru.jdev.constants.CommonConstants;
import ru.jdev.constants.HtmlConstants;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;
import ru.jdev.html.forms.FormImpl;

public class Task implements ACSUIEditable {
    private String formulation;
    private TaskPartition partition;
    private TaskData[] data;
    private String id;
    private int days_to_resolve;

    public String getLevel() {
        return level;
    }

    private String level;

    Task(String _formulation, TaskPartition _partition, TaskData[] _data, String _id, int _days_to_resolve, String _level) {
        formulation = _formulation;
        partition = _partition;
        data = _data;
        id = _id;
        days_to_resolve = _days_to_resolve;
        level = _level;
    }

    public String getFormulation() {
        return formulation;
    }

    public TaskPartition getPartition() {
        return partition;
    }

    public TaskData[] getData() {
        return data;
    }

    @Override
    public String toString() {
        StringBuffer bfr = new StringBuffer();
        bfr.append("Formulation: ");
        bfr.append(formulation);
        bfr.append(CommonConstants.SEPARATOR_LINE);
        bfr.append("Partition: ");
        bfr.append(partition);
        bfr.append(CommonConstants.SEPARATOR_LINE);
        bfr.append("data count = ");
        bfr.append(data.length);
        bfr.append(CommonConstants.SEPARATOR_LINE);
        for (TaskData td : data) {
            bfr.append("Input data (");
            bfr.append(td.input_data.size());
            bfr.append("):");
            bfr.append(CommonConstants.SEPARATOR_LINE);
            for (IVariable v : td.input_data) {
                bfr.append(v.getType().getName());
                bfr.append(" : ");
                bfr.append(v.getName());
                bfr.append(" = ");
                bfr.append(v.getVarValue());
                bfr.append(CommonConstants.SEPARATOR_LINE);
            }
            bfr.append(CommonConstants.SEPARATOR_LINE);
            bfr.append("Input data (");
            bfr.append(td.input_data.size());
            bfr.append("):");
            bfr.append(CommonConstants.SEPARATOR_LINE);
            for (IVariable v : td.input_data) {
                bfr.append(v.getType().getName());
                bfr.append(" : ");
                bfr.append(v.getName());
                bfr.append(" = ");
                bfr.append(v.getVarValue());
                bfr.append(CommonConstants.SEPARATOR_LINE);
            }
        }
        return bfr.toString();
    }

    public String getLabel() {
        return id;
    }

    public int getId() {
        return Integer.parseInt(id);
    }

    public FormImpl getForm(String type, SimpleRequestContext src) {
        boolean isInsert = ACSUIEditable.FORM_TYPE_INSERT.equalsIgnoreCase(type);
        FormImpl f = new FormImpl("acsform");
        f.setMethod("POST");
        f.setName("task_form");
        ((FormImpl) f).setFormUserName("Форма " + (isInsert ? " добавления" : " обновления") + " задания");

        TextareaElement formulation = new TextareaElement();
        formulation.setName("forml");
        formulation.setUserName(SystemManager.getText("acs2.user.gettask.formulation"));
        formulation.setCols(40);
        formulation.setRows(15);
        formulation.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        formulation.setErrorMessage(SystemManager.getText("acs2.user.gettask.formulation_not_null"));

        SelectElement part = new SelectElement(TaskPartitionsManager.getOptions(), "part", SystemManager.getText("acs2.user.gettask.partition"), "");

        TextElement days_to_resolve = new TextElement("dtr", "", SystemManager.getText("acs2.user.gettask.days_to_resolve"));
        days_to_resolve.setCheckJs(HtmlConstants.CHECK_NUMBER);
        days_to_resolve.setErrorMessage(SystemManager.getText("acs2.user.gettask.days_to_resolve_is_number"));
        TextElement level = new TextElement("level", "", SystemManager.getText("acs2.user.gettask.level"));
        level.setCheckJs(HtmlConstants.CHECK_LEVEL);
        level.setErrorMessage(SystemManager.getText("acs2.user.gettask.level_is"));

        TextareaElement input = new TextareaElement();
        input.setName("input");
        input.setUserName(SystemManager.getText("acs2.user.gettask.input"));
        input.setCols(40);
        input.setRows(15);

        TextareaElement output = new TextareaElement();
        output.setName("output");
        output.setUserName(SystemManager.getText("acs2.user.gettask.output"));
        output.setCols(40);
        output.setRows(15);

        f.addElement(formulation);
        f.addElement(part);
        f.addElement(days_to_resolve);
        f.addElement(level);
        if (isInsert) {
            f.addElement(input);
            f.addElement(output);
        } else {
            f.addElement(new LinkElement("javascript: window.open(\"popup_index.jsp?page=acseditablemanag&type=task_data&tid=" + id+ "\",\"\",\"toolbar=no,height=480, width=640, resizable=yes, menubar=no, scrollbars=yes\"); return false;", "", SystemManager.getText("acs2.user.gettask.in_out_link"), SystemManager.getText("update2")));
        }
        f.addElement(new SubmitElement("submit", isInsert ? SystemManager.getText("add") : SystemManager.getText("update")));
        return f;
    }

    public void fillForm(FormImpl form, String type) {
        ((HtmlFormInputElement) form.getElementByName("forml")).setValue(formulation);
        ((HtmlFormInputElement) form.getElementByName("part")).setValue(partition.getId() + "");
        ((HtmlFormInputElement) form.getElementByName("dtr")).setValue(days_to_resolve + "");
        ((HtmlFormInputElement) form.getElementByName("level")).setValue(level);
    }

    public FormImpl getFilledForm(String type) {
        FormImpl f = getForm(type, null);
        fillForm(f, type);
        return f;
    }

    public String getEditableName1() {
        return SystemManager.getText("acs2.user.gettask.editable_name");
    }

    public String getEditableName2() {
        return getEditableName1();
    }

    public String[] toStringArray() {
        return new String[]{id, formulation, partition.getName(), days_to_resolve + "", level};
    }

    public int getDays_to_resolve() {
        return days_to_resolve;
    }
}
