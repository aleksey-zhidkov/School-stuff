<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext" %>
<%@ page import="ru.lexx.acsystem.backend.constants.ACSConstants" %>
<%@ page import="ru.jdev.html.forms.FormImpl" %>
<%@ page import="ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.lexx.acsystem.backend.ACSUIEditor" %>
<%
    SimpleRequestContext src = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
    ACSUIEditor editor = (ACSUIEditor) src.getObject(ACSConstants.ACSUIEDITOR_ATTRIBUTE_NAME);
%>
<a href="index.jsp?page=acseditablemanag&type=<%= editor.getType() %><%= ("task_data".equalsIgnoreCase(editor.getType()) ? "&tid="+src.getInt("tid") : "")%>"><%= SystemManager.getText("acs2.common.back")%></a>
<%
    String[] flags = editor.getErrorFlagNames();
    for (int i = 0; i < flags.length; i++) {
        if (src.hasFlag(flags[i]))
            out.println(SystemManager.getText(editor.getErrorMessageKey(flags[i])));
    }
    FormImpl f = (FormImpl) src.getObject(ACSConstants.FORM_ATTRIBUTE_NAME);
    out.println(f.getHtmlWithScript());
%>
<a href="index.jsp?page=acseditablemanag&type=<%= editor.getType() %>"><%= ("task_data".equalsIgnoreCase(editor.getType()) ? "&tid="+src.getInt("tid") : "")%><%= SystemManager.getText("acs2.common.back")%></a>