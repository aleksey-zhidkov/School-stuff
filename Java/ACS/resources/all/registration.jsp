<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.system.SystemManager,
                 ru.lexx.acsystem.backend.utils.ACSStringUtils" %>
<%@ page import="ru.jdev.html.forms.FormImpl" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<br>
<%
    SimpleRequestContext context = (SimpleRequestContext) request.getAttribute("context");
    if (context.hasParameter("succsess") && context.getBoolean("succsess")) {
        Map mParams = new HashMap();
        mParams.put("message_header", SystemManager.getText("acs2.index.you_re_registered"));
        mParams.put("message_body", SystemManager.getText("regsuccess"));
        out.println(ACSStringUtils.processTemplate("message", mParams));
        return;
    }
    out.println(((FormImpl) context.getObject("form")).getHtmlWithScript());
%>