<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.system.SystemManager,
                 ru.lexx.acsystem.backend.task.Task" %>
<%
    SimpleRequestContext src = (SimpleRequestContext) request.getAttribute("context");
    Task task = ((Task) src.getObject("task"));
    if (task != null) {
%>
<table width="80%" cellpadding="1px" cellspacing="0px" style="background-color: #f2f4fa; border: 1px solid black">
    <tr>
        <td colspan="4" style="text-align: right; line-height:14px; font-size: 12px; padding:5px;" class="header2">
            <%= SystemManager.getText("acs2.user.gettask.your_task")%>
        </td>
    </tr>
    <tr style="background-color: #E9EAED;">
        <td width="40%" style="padding: 10px; border-bottom: 1px solid black;"><%= SystemManager.getText("need_resolve_to")%></td>
        <td width="10%" style="padding: 10px; border-bottom: 1px solid black;"><%= src.getString("need_resolve_to")%></td>
        <td width="10%" style="padding: 10px; border-bottom: 1px solid black;"><%= SystemManager.getText("acs2.user.gettask.partition")%></td>
        <td width="40%" style="padding: 10px; border-bottom: 1px solid black;" align="left"><%= ((Task) src.getObject("task")).getPartition().getName()%></td>
    </tr>
    <tr>
        <td style="padding: 20px" width="200px"><%= SystemManager.getText("formulation")%></td>
        <td style="padding: 20px" colspan="3" width="100%" align="justify"><%= task.getFormulation()%></td>
    </tr>
    <tr>
        <td colspan="4" style="text-align: right; line-height:14px; font-size: 12px; padding:5px;" class="header2">
            <%= SystemManager.getText("acs2.user.gettask.your_task")%>
        </td>
    </tr>
</table>
<%
    }
%>