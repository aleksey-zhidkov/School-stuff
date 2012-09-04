package ru.lexx.acsystem.webinterface.phandlers.admin;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.jdev.requesthandling.forward.Forward;
import ru.jdev.requesthandling.forward.RedirectJSPForward;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditManager;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditable;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditor;
import ru.lexx.acsystem.backend.uiediatble.ACSUIFilter;
import ru.lexx.acsystem.webinterface.ACSRequestContext;
import ru.jdev.html.forms.FormImpl;
import ru.jdev.html.forms.elements.TextElement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 19.02.2006
 * Time: 22:32:35
 */
public class ACSEditableManagmentPageHandler implements PageHandler {

    public void init(Map params) {
    }

    public Forward handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        SimpleRequestContext src = (SimpleRequestContext) context;
        ACSUIEditor editor = ACSUIEditManager.getEditor(src.getString("type"));
        src.setObject(ACSConstants.ACSUIEDITOR_ATTRIBUTE_NAME, editor);
        context.setObject("title", editor.getManagmentLabel());
        if ("delete".equalsIgnoreCase(src.getNotNullString("action")))
            doDelete(src, request);
        else if ("updateacseditable".equalsIgnoreCase(src.getString("page")) ||
                 "addacseditable".equalsIgnoreCase(src.getString("page")))
            doUpdate(src, request);
        if (src.hasParameter("filter") && src.getInt("filter") != 0)
            ((ACSRequestContext) context).addMessage(new StatusMessage(((ACSUIFilter) editor).getFilterMessage((ACSRequestContext) context), StatusMessageType.INFO_MESSAGE));
        return new RedirectJSPForward(request.getQueryString());
    }

    private static void doUpdate(SimpleRequestContext src, HttpServletRequest request) {
        boolean isUpdate = "updateacseditable".equalsIgnoreCase(src.getString("page"));
        src.setObject("action_name", isUpdate ? "acs2.admin.usermanagment.updated" : "action_name=acs2.admin.usermanagment.inserted");
        ACSUIEditor editor = ACSUIEditManager.getEditor(src.getString("type"));
        src.setObject(ACSConstants.ACSUIEDITOR_ATTRIBUTE_NAME, editor);
        FormImpl f;
        TextElement act;
        if (isUpdate) {
            src.setObject("title", editor.getUpdateLabel());
            ACSUIEditable ed = editor.getEditable(src);
            f = ed.getFilledForm(ACSUIEditable.FORM_TYPE_UPDATE);
            act = new TextElement("action", "update", "");
        } else {
            src.setObject("title", editor.getAddLabel());
            f = editor.getEditable().getForm(ACSUIEditable.FORM_TYPE_INSERT, src);
            act = new TextElement("action", "insert", "");
        }

        act.setType("hidden");
        f.addElement(act);
        if (src.hasParameter("action")) {
            ACSUIEditable e;
            if (isUpdate)
                e = editor.updatetEditable(src);
            else
                e = editor.insertEditable(src);
            src.setBoolean("succsess", e != null);
            if (e == null) {
                src.setObject(ACSConstants.FORM_ATTRIBUTE_NAME, f);
                return;
            }
            request.getSession().setAttribute(ACSConstants.ACTIONED_EDITABLE_ATTRIBUTE_NAME, e);
            src.setObject("page", "acseditablemanag");
        }
        src.setObject(ACSConstants.FORM_ATTRIBUTE_NAME, f);
    }

    private static void doDelete(SimpleRequestContext src, HttpServletRequest request) {
        ACSUIEditor editor = (ACSUIEditor) src.getObject(ACSConstants.ACSUIEDITOR_ATTRIBUTE_NAME);
        ACSUIEditable e = editor.deleteEditable(src);
        request.getSession().setAttribute(ACSConstants.ACTIONED_EDITABLE_ATTRIBUTE_NAME, e);
        src.setBoolean("success", e != null);
        src.setObject("action_name", "acs2.admin.usermanagment.deleted");
    }

    public void destroy() {
    }
}
