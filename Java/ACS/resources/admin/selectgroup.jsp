<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.constants.ACSConstants,
                 ru.jdev.html.forms.FormImpl" %>
<%@ page import="ru.jdev.html.forms.elements.SelectElement" %>
<%
    SimpleRequestContext src = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
    FormImpl f = (FormImpl) src.getObject(ACSConstants.FORM_ATTRIBUTE_NAME);
    if (src.hasParameter((String) src.getObject("name"))) {
        ((SelectElement) f.getElementByName((String) src.getObject("name"))).setValue(src.getString((String) src.getObject("name")));
    }
    out.print(f.getHtmlWithScript());
%>