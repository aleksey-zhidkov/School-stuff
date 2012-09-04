<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.constants.ACSConstants" %>
<%@ page import="ru.lexx.acsystem.backend.events.Event" %>
<%@ page import="ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.lexx.acsystem.backend.user.UserAccaunt" %>
<%@ page import="ru.jdev.html.forms.FormImpl" %>
<%@ page import="ru.jdev.utils.string.StringUtils"%>
<%
    SimpleRequestContext src = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
    FormImpl f = (FormImpl) src.getObject(ACSConstants.FORM_ATTRIBUTE_NAME);
    out.print(f.getHtmlWithScript());
    if (src.hasParameter("events")) {
        UserAccaunt accaunt = (UserAccaunt) src.getObject("view_accaunt");
%>
<table width="80%" cellpadding="0" cellspacing="0" style="font-size:11px; font-family: verdana;">
    <tr align="center" style="background-color: #778899;">
        <td style="border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;">
            <%= SystemManager.getText("acs2.admin.history.event")%>
        </td>
        <td style="border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black">
            <%= SystemManager.getText("acs2.admin.history.date")%>
        </td>
    </tr>
    <%
        Event[] evts = (Event[]) src.getObject("events");
        int page_list = src.getInt("page_list");
        if (page_list == 0)
            page_list = 1;
        int from = (page_list - 1) * 10;
        int to = from + 10 < evts.length ? from + 10 : evts.length;
        for (int i = from; i < to; i++) {
            out.println("<tr align=\"center\" " + ((i % 2 == 0) ? (" style=\"line-height: 25px;\"") : (" style=\"background-color: #dbe1e8; line-height: 25px;\"")) + ">");
            out.println("<td style=\"border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;\">");
            out.println(evts[i].getDescription());
            out.println("</td>");
            out.println("<td width=\"100px\" style=\"border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black\">");
            out.println(StringUtils.getFormattedDate(evts[i].getDate()));
            out.println("</td>");
            out.println("</tr>");
        }
    %>
</table>
<div>
    <%
        int pages = (int) Math.ceil((double) evts.length / 10);
        if (pages == 1)
            pages = 0;
        for (int i = 1; i <= pages; i++) {
            out.println("<a href=\"index.jsp?page=userhistory&page_list=" + i + "&uid=" + accaunt.getId() + "\">" + i + "</a>");
        }
    %>
</div>
<%
    }

%>