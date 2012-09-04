<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext" %>
<%@ page import="ru.lexx.acsystem.backend.constants.ACSConstants" %>
<%@ page import="ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.lexx.acsystem.backend.uiediatble.ACSUIEditor" %>
<%@ page import="ru.jdev.html.forms.FormImpl" %>
<%
    String pageName = SystemManager.getProperty("SERVER_URL") + request.getServletPath();
    SimpleRequestContext src = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
    FormImpl f = (FormImpl) src.getObject(ACSConstants.FORM_ATTRIBUTE_NAME);
    ACSUIEditor editor = (ACSUIEditor) src.getObject(ACSConstants.ACSUIEDITOR_ATTRIBUTE_NAME);
    out.println("<a href='"+pageName+"?page=acseditablemanag&type=" + editor.getType() + ("task_data".equalsIgnoreCase(editor.getType()) ? "&tid=" + src.getInt("tid") : "") + "'>" + SystemManager.getText("acs2.common.back") + "</a>");
    out.println(f.getHtmlWithScript());
    out.println("<a href='"+pageName+"?page=acseditablemanag&type=" + editor.getType() + ("task_data".equalsIgnoreCase(editor.getType()) ? "&tid=" + src.getInt("tid") : "") + "'>" + SystemManager.getText("acs2.common.back") + "</a>");
%>