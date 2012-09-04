<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.jdev.utils.string.Converter,
                 ru.jdev.utils.string.StringUtils,
                 ru.lexx.acsystem.backend.system.SystemManager,
                 ru.lexx.acsystem.backend.task.stat.RecivedProgram"%>

<%
    SimpleRequestContext src = (SimpleRequestContext) request.getAttribute("context");
    RecivedProgram[] prs = (RecivedProgram[]) src.getObject("rec_programs");
%>
<table width="80%" cellpadding="0" cellspacing="0" style="font-size:11px; font-family: verdana;">
    <tr align="center" style="background-color: #778899;">
        <td style="border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;">
            <%= SystemManager.getText("acs2.user.senttasks.task")%>
        </td>
        <td style="border-top:1px solid black;border-bottom:1px solid black">
            <%= SystemManager.getText("acs2.user.senttasks.source")%>
        </td>
        <td style="border-top:1px solid black;border-bottom:1px solid black">
            <%= SystemManager.getText("acs2.user.senttasks.date")%>
        </td>
        <td style="border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black">
            <%= SystemManager.getText("acs2.user.senttasks.isRight")%>
        </td>
    </tr>
    <%
        int page_list = src.getInt("page_list");
        if (page_list == 0)
            page_list = 1;
        int from = (page_list - 1) * 10;
        int to = from + 10 < prs.length ? from + 10 : prs.length;
        for (int i = from; i < to; i++) {
    %>
    <tr <%= i % 2 == 0 ? "" : "style=\"background-color: #dbe1e8;\""%>>
        <td style="padding: 10px; border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;" align="center">
            <%= prs[i].getTask_forml()%>
        </td>
        <td style="text-decoration: underline; cursor: pointer; border-top:1px solid black;border-bottom:1px solid black">
            <a onclick='javascript: window.open("popup_index.jsp?page=viewprogram&no_status=true&rid=<%= prs[i].getId() %>","message","toolbar=no,height=480, width=640, resizable=yes, menubar=no, scrollbars=yes"); return false;'><%= SystemManager.getText("view")%></a>
        </td>
        <td width="100px" style="border-top:1px solid black;border-bottom:1px solid black" align="center">
            <%= StringUtils.getFormattedDate(prs[i].getRecive_date())%>
        </td>
        <td style="border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black" align="center">
            <%= prs[i].isOk() ? SystemManager.getText("acs2.user.senttasks.right") : SystemManager.getText("acs2.user.senttasks.mistake")%>
        </td>
    </tr>
    <%
        }
    %>
</table>
<div>
    <%
        int pages = (int) Math.ceil((double) prs.length / 10);
        if (pages == 1)
            pages = 0;
        for (int i = 1; i <= pages; i++) {
            out.println("<a href=\"index.jsp?page=senttasks&page_list=" + i + "&uid=" + src.getInt("uid") + (src.hasParameter("admin") ? "&admin=true" : "") +"\">" + i + "</a>");
        }
    %>
</div>