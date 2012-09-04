<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.jdev.utils.string.StringUtils,
                 ru.lexx.acsystem.backend.system.SystemManager,
                 java.util.Calendar" %>
<%@ page import="ru.lexx.acsystem.webinterface.ACSRequestContext"%>
<%
    ACSRequestContext src = (ACSRequestContext) request.getAttribute("context");
    if (src.hasFlag("no_task"))
        return;
    String forml = src.getString("forml");
    Calendar give_date = (Calendar) src.getObject("give_date");
    Calendar need_date = (Calendar) src.getObject("need_date");
%>
<table width="80%" cellpadding="1px" cellspacing="0px" style="background-color: #f2f4fa; border: 1px solid black">
    <tr>
        <td colspan="4" style="text-align: right; line-height:14px; font-size: 12px; padding:5px;" class="header2">
            <%= SystemManager.getText("acs2.user.gettask.your_task")%>
        </td>
    </tr>
    <tr style="background-color: #E9EAED;">
        <td width="40%" style="padding: 10px; border-bottom: 1px solid black;"><%= SystemManager.getText("need_resolve_to")%></td>
        <td width="10%" style="padding: 10px; border-bottom: 1px solid black;"><%= StringUtils.getFormattedDate(need_date.getTime())%></td>
        <td width="30%" style="padding: 10px; border-bottom: 1px solid black;"><%= SystemManager.getText("task_gived")%></td>
        <td width="20%" style="padding: 10px; border-bottom: 1px solid black;" align="left"><%= StringUtils.getFormattedDate(give_date.getTime())%></td>
    </tr>
    <tr>
        <td style="padding: 20px" width="200px"><%= SystemManager.getText("formulation")%></td>
        <td style="padding: 20px" colspan="3" width="100%" align="justify"><%= forml%></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align: right; line-height:14px; font-size: 12px; padding:5px;" class="header2">
            <%= SystemManager.getText("acs2.user.gettask.your_task")%>
        </td>
    </tr>
</table>