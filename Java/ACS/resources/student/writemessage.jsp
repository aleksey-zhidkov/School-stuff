<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.constants.ACSConstants,
                 ru.jdev.html.forms.FormImpl" %>
<%
    SimpleRequestContext context = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
    FormImpl f = (FormImpl) context.getObject(ACSConstants.FORM_ATTRIBUTE_NAME);
    out.println(f.getHtmlWithScript());
%>