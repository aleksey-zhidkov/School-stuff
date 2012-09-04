<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.jdev.html.forms.FormImpl" %>
<br>

<%
    SimpleRequestContext context = (SimpleRequestContext) request.getAttribute("context");
    if (context.hasParameter("success") && context.getBoolean("success"))
        return;
    FormImpl form = (FormImpl) context.getObject("form");
    out.println(form.getHtmlWithScript());
%>