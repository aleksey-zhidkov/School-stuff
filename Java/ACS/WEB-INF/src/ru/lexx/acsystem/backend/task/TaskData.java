package ru.lexx.acsystem.backend.task;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.interpretator.common.variable.IVariable;
import ru.lexx.acsystem.interpretator.common.variable.IVariableManager;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.HtmlFormInputElement;
import ru.jdev.html.forms.elements.SubmitElement;
import ru.jdev.html.forms.elements.TextElement;
import ru.jdev.html.forms.elements.TextareaElement;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class TaskData implements ACSUIEditable {

    int id;
    int task_id;

    List<IVariable> input_data;
    List<IVariable> output_data;

    TaskData(List<IVariable> _ida, List<IVariable> _od, int _id, int _task_id) {
        input_data = _ida;
        output_data = _od;
        id = _id;
        task_id = _task_id;
    }

    public List<IVariable> getInput_data() {
        return input_data;
    }

    public List<IVariable> getOutput_data() {
        return output_data;
    }

    public String getLabel() {
        return id + "";
    }

    public int getId() {
        return id;
    }

    public FormImpl getForm(String type, SimpleRequestContext src) {
        boolean isInsert = ACSUIEditable.FORM_TYPE_INSERT.equalsIgnoreCase(type);
        FormImpl f = new FormImpl("acsform");
        f.setMethod("POST");
        f.setName("task_data_form");
        String page = isInsert ? "addacseditable" : "updateacseditable";
        ((FormImpl) f).setFormUserName("‘орма " + (isInsert ? " добавлени€" : " обновлени€") + " набора данных");
        f.setAction("popup_index.jsp?page=" + page + "&type=task_data&tid=" + (isInsert ? src.getString("tid") : task_id+""));

        TextElement eid = new TextElement("id", id + "", "");
        eid.setType("hidden");

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

        f.addElement(eid);
        f.addElement(input);
        f.addElement(output);
        f.addElement(new SubmitElement("submit", isInsert ? SystemManager.getText("add") : SystemManager.getText("update")));
        return f;
    }

    public void fillForm(FormImpl form, String type) {
        ((HtmlFormInputElement) form.getElementByName("input")).setValue(dataToString(input_data));
        ((HtmlFormInputElement) form.getElementByName("output")).setValue(dataToString(output_data));
    }

    public FormImpl getFilledForm(String type) {
        FormImpl f = getForm(type, null);
        fillForm(f, type);
        return f;
    }

    public String getEditableName1() {
        return "1";
    }

    public String getEditableName2() {
        return "набор";
    }

    public String[] toStringArray() {
        return new String[]{id + "", dataToString(input_data), dataToString(output_data)};
    }

    public static String dataToString(List<IVariable> data) {
        StringBuffer sdata = new StringBuffer();
        for (IVariable v : data) {
            sdata.append(v.getName()).append("(").append(v.getType().getCode()).append(")").append("=").append(v.getVarValue().toString()).append(";");
        }
        if (sdata.length() > 0)
            sdata.deleteCharAt(sdata.length() - 1);
        return sdata.toString();
    }

    public static List<IVariable> parseTaskData(IVariableManager var_manager, String str) {
        List<IVariable> lst = new ArrayList<IVariable>();
        if (str == null || str.length() == 0)
            return lst;
        StringTokenizer st = new StringTokenizer(str, ";");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            int pos1 = token.indexOf("(");
            int pos2 = token.indexOf(")");
            int pos3 = token.indexOf("=");
            String vname = token.substring(0, pos1);
            String type = token.substring(pos1 + 1, pos2);
            String value = token.substring(pos3 + 1);
            lst.add(var_manager.createVariable(var_manager.getInternalType(type), vname, value));
        }
        return lst;
    }
}
