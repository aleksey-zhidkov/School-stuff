<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.constants.ACSConstants,
                 ru.lexx.acsystem.backend.system.SystemManager,
                 ru.lexx.acsystem.backend.task.stat.CheckResult,
                 ru.lexx.acsystem.backend.task.stat.UserStatistic" %>
<%@ page import="ru.jdev.utils.string.StringUtils"%>
<%
    SimpleRequestContext ctx = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
    UserStatistic stat = (UserStatistic) ctx.getObject("stat");
%>
<table cellpadding="0" cellspacing="0" style="width: 250px; background-color:  #f2f4fa; border: 1px solid black">
    <tr>
        <td colspan="2" style="text-align: right; line-height:14px; font-size: 12px; padding:5px;" class="header2">
            <%= SystemManager.getText("viewstat_page_name")%>
        </td>
    </tr>
    <tr>
        <td>
            <%= SystemManager.getText("acs2.user.viewstat_user.tasks_resolved")%>
        </td>
        <td align="center">
            <%= stat.getResolvesCount() %>
        </td>
    </tr>
    <tr>
        <td>
            <%= SystemManager.getText("acs2.user.viewstat_user.max_point")%>
        </td>
        <td align="center">
            <%= stat.getMaxPoints()  %>
        </td>
    </tr>
    <tr>
        <td>
            <%= SystemManager.getText("acs2.user.viewstat_user.min_point")%>
        </td>
        <td align="center">
            <%= stat.getMinPoints()  %>
        </td>
    </tr>
    <tr>
        <td>
            <%= SystemManager.getText("acs2.user.viewstat_user.avg_point")%>
        </td>
        <td align="center">
            <%= stat.getAvgPoints()  %>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: right; line-height:14px; font-size: 12px; padding:5px;" class="header2">
            <%= SystemManager.getText("viewstat_page_name")%>
        </td>
    </tr>
</table>
<br/>
<jsp:include page="../admin/visualuserstat.jsp"/>
<br/>

<table width="80%" cellpadding="1px" cellspacing="0px" style="background-color: #f2f4fa; border: 1px solid black">
    <tr>
        <td colspan="4"
            style="text-align: right; line-height:14px; font-size: 12px; padding:5px;background-color:  #E9EAED; border-top: 0px solid black"
            class="header2"><%= SystemManager.getText("acs2.user.viewstat_user.checked_resolves")%>
        </td>
    </tr>
    <tr style="background-color: #778899;">
        <td colspan="2" style="border-top:1px solid black;border-bottom:1px solid black;" width="80%" align="center"><%= SystemManager.getText("acs2.user.viewstat_user.task")%></td>
        <td style="border-top:1px solid black;border-bottom:1px solid black" width="15%" align="center"><%= SystemManager.getText("acs2.user.viewstat_user.date")%></td>
        <td style="border-top:1px solid black;border-bottom:1px solid black;" width="5%" align="center"><%= SystemManager.getText("acs2.user.viewstat_user.points")%></td>
    </tr>
    <%
        CheckResult[] data = stat.getResolves();
        for (int i = 0; i < data.length; i++) {
    %>
    <tr>
        <td style="border-bottom:1px solid black;"><%= data[i].getTaskId()%></td>
        <td style="border-bottom:1px solid black;"><a style="cursor: pointer; text-decoration: underline;" onclick='javascript: window.open("popup_index.jsp?page=viewtask&tid=<%= data[i].getTaskId() %>","message","toolbar=no,height=480, width=640, resizable=yes, menubar=no, scrollbars=yes"); return false;'><%= SystemManager.getText("view")%></a></td>
        <td style="border-bottom:1px solid black;" align="center" width="100px"><%= StringUtils.getFormattedDate(data[i].getCheckDate())%></td>
        <td style="border-bottom:1px solid black;" align="center"><%= data[i].getMark()%></td>
    </tr>
    <%
        }
    %>
    <tr>
        <td colspan="4"
            style="text-align: right; line-height:14px; font-size: 12px; padding:5px;background-color:  #E9EAED; border-top: 0px solid black"
            class="header2"><%= SystemManager.getText("acs2.user.viewstat_user.checked_resolves")%>
        </td>
    </tr>
</table>