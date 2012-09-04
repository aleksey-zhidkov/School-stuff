<%!public static StatusMessage mes = new StatusMessage(SystemManager.getText("acs2.index.page_loaded"), StatusMessageType.INFO_MESSAGE);%><%
    try {
%>
<%@ page errorPage="./errorPage.jsp" %>
<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.site.StatusMessage,
                 ru.lexx.acsystem.backend.site.StatusMessageType,
                 ru.lexx.acsystem.backend.system.SystemManager,
                 ru.lexx.acsystem.backend.user.UserAccaunt" %>
<%@ page import="ru.lexx.acsystem.backend.user.UserManager" %>
<%@ page import="ru.lexx.acsystem.backend.utils.ACSStringUtils" %>
<%@ page import="ru.lexx.acsystem.webinterface.ACSRequestContext" %>
<%@ page import="ru.lexx.acsystem.webinterface.PageManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%
    if (SystemManager.isSystemFail()) {
        response.sendRedirect("total_fail.htm");
    }
    UserAccaunt accaunt = (UserAccaunt) request.getSession().getAttribute("accaunt");
    if (accaunt == null)
        request.getSession(true).setAttribute("accaunt", UserManager.noneAccaunt);
    SimpleRequestContext ctx = (SimpleRequestContext) request.getAttribute("context");
    String ipage = ctx.getString("page");
%>
<%@ page contentType="text/html; charset=cp1251" %>
<html>
<head>
    <link href="code/style.css" rel="stylesheet" level="text/css">
    <title>Stack -> <%= ctx.getNotNullString("title")%></title>

</head>

<body>
<table cellpadding=0 cellspacing=0 width=100% height=123px>
    <tr>
        <td width=583px><img src="images/caption.gif"></td>
        <td width=100% style="background-image: url(images/caption_l.gif)"></td>
    </tr>
</table>
<table cellpadding=0 cellspacing=5 width=100% height="75%">
    <tr valign=top>
        <!-- Меню -->
        <td width=149px class=coll>
            <jsp:include page="menu.jsp"/>
        </td>
        <!-- Новости -->
        <td id="news" width=149px class="colc2">
            <jsp:include page="news.jsp"/>
        </td>
        <!-- Основная часть -->
        <td class="colc">
            <%
                Map params = new HashMap();
                params.put("title", ctx.getObject("title"));
                out.println(ACSStringUtils.processTemplate("header", params));
            %>
            <div>
                <table cellpadding="20px" cellspacing="0px" width="100%" height="100%">
                    <tr>
                        <%
                            if (!ctx.hasParameter("no_status")) {
                        %>
                        <td valign="top" width="150px" heigth="100%">
                            <table cellpadding="0" cellspacing="0" width="100%" heigth="100%"
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
                        </td>
                        <%
                            }
                        %>
                        <td valign="top" align="left" style="margin: 50px">
                            <%
                                    String file = PageManager.getPage(ipage);
                                    if (file != null) {
                                        pageContext.include(file);
                                    } else {
                                        Map mParams = new HashMap();
                                        mParams.put("message_header", SystemManager.getText("acs2.index.welcome"));
                                        mParams.put("message_body", SystemManager.getText("intro"));
                                        out.println(ACSStringUtils.processTemplate("message", mParams));
                                    }
                                } catch (Throwable t) {
                                    SystemManager.getLogger().log("index.jsp", t);
                                    throw t;
                                }
                            %>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
<script language="javascript">
    if (screen.width < 1200)
        news.style.display = "none";
</script>
<table width=100% height=23px cellpadding=0 cellspacing=0>
    <tr><td style="background-image: url(images/foot.gif)"><div align=right><%= SystemManager.getText("copy")%></div>
        <br>
    </td></tr>
</table>
</body>
</html>
