<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.constants.ACSConstants,
                 ru.jdev.html.forms.FormImpl" %>
<%@ page import="ru.jdev.html.forms.elements.SelectElement" %>
<%
    SimpleRequestContext src2 = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
    FormImpl f = (FormImpl) src2.getObject(ACSConstants.FORM_ATTRIBUTE_NAME);
    if (src2.hasParameter((String) src2.getObject("name"))) {
        ((SelectElement) f.getElementByName((String) src2.getObject("name"))).setValue(src2.getString((String) src2.getObject("name")));
    }
    out.print(f.getHtmlWithScript());
%>