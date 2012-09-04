<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext" %>
<%@ page import="ru.lexx.acsystem.backend.constants.ACSConstants" %>
<%@ page import="ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.lexx.acsystem.backend.user.UserAccaunt" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.jdev.utils.string.StringUtils"%>
<table width="80%" cellpadding="0" cellspacing="0" style="font-size:11px; font-family: verdana; background-color: #f2f4fa;">
    <tr align="center" style="background-color: #778899;">
        <td style="border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;"><%= SystemManager.getText("acs2.admin.active_users.user")%></td>
        <td style="border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black"><%= SystemManager.getText("acs2.admin.active_users.date")%></td></tr>
    <%
        SimpleRequestContext ctx = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
        List sess = (List) ctx.getObject("active_sessions");
        for (int i = 0; i < sess.size(); i++) {
            HttpSession s = (HttpSession) sess.get(i);
            out.println("<tr align=\"center\" "+ ((i % 2 == 0) ? ("") : ("style=\"background-color: #dbe1e8;\""))+">");
            out.println("<td style=\"border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;\">");
            out.println(((UserAccaunt) s.getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME)).getLogin());
            out.println("</td>");
            out.println("<td style=\"border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black\">");
            out.println(StringUtils.getFormattedDateTime(new Date(s.getLastAccessedTime())));
            out.println("</td>");
            out.println("</tr>");
        }
    %>
</table>