<%!public static StatusMessage mes = new StatusMessage(SystemManager.getText("acs2.index.page_loaded"), StatusMessageType.INFO_MESSAGE);%>
<%
    try {
%>
<%@ page errorPage="./errorPage.jsp" %>
<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.site.StatusMessage,
                 ru.lexx.acsystem.backend.site.StatusMessageType,
                 ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.lexx.acsystem.backend.utils.ACSStringUtils" %>
<%@ page import="ru.lexx.acsystem.webinterface.ACSRequestContext" %>
<%@ page import="ru.lexx.acsystem.webinterface.PageManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
    SimpleRequestContext ctx = (SimpleRequestContext) request.getAttribute("context");
    String ipage = ctx.getString("page");
%>
<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
    <link href="code/style.css" rel="stylesheet" level="text/css">
    <title>Stack -> <%= ctx.getNotNullString("title")%></title>

</head>

<body style="background-image: url('images/bg.gif');">
<%
    Map params = new HashMap();
    params.put("title", ctx.getObject("title"));
    out.println(ACSStringUtils.processTemplate("header", params));
%>
<div style="padding:20px">
    <%
        if (!ctx.hasParameter("no_status")) {
    %>
    <table cellpadding="0" cellspacing="0"
           style="border: 1px solid black">
        <tr>
            <td align="right" class="header">
                <%= SystemManager.getText("acs2.index.op_status")%>
            </td>
        </tr>
        <tr>
            <td align="left"
                style="border: 1px solid black; font-size: 12px; padding:12px; line-height:14px">
                <ul style="margin-left: 15px">
                    <%
                        ACSRequestContext actx = (ACSRequestContext) ctx;
                        actx.addMessage(mes);
                        ArrayList statuses = actx.getMessages();
                        if (statuses != null)
                            for (int i = 0; i < statuses.size(); i++)
                                out.println(((StatusMessage) statuses.get(i)).getListItem());
                    %>
                </ul>
            </td>
        </tr>
        <tr>
            <td align="right" class="header">
                &nbsp
            </td>
        </tr>
    </table>
    <br>
    <%}
        String file = PageManager.getPage(ipage);
        pageContext.include(file);
    %>
</div>
</body>
</html>
<%
    } catch (Throwable t) {
        SystemManager.getLogger().log("index.jsp", t);
        throw t;
    }
%>