<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext" %>
<%@ page import="ru.jdev.utils.string.StringUtils" %>
<%@ page import="ru.lexx.acsystem.backend.constants.ACSConstants" %>
<%@ page import="ru.lexx.acsystem.backend.messenger.Message" %>
<%@ page import="ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.jdev.utils.string.Converter"%>
<%
    Message m = (Message) ((SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME)).getObject("message");
%>
<br>
<table align="center" width="75%" style="border:1px solid black;font-size:11px; font-family: verdana;" cellpadding="0" cellspacing="0">
    <tr>
    <th colspan="4" class="header3" style="padding:5px">
        <%= SystemManager.getText("message_from_user")%> <%= m.getFromLogin()%>
    </th>
    </tr>
    <tr class="header3" style="background-color: #E9EAED;">
        <td align="right"><%= SystemManager.getText("acs2.user.messages.from")%></td>
        <td align="left"><%= m.getFromLogin() %></td>
        <td align="right"><%= SystemManager.getText("acs2.user.messages.sentDate")%></td>
        <td align="left"><%= StringUtils.getFormattedDateTime(m.getSentDate()) %></td>
    </tr>
    <tr>
    </tr>
    <tr>
    <th colspan="4" class="text" align="justify" style="font-weight: normal; border-top: 1px solid black;background-color: #f2f4fa; padding: 20px; padding:5px; font-size:13px;">
    <%= m.getText()%>
    </th>
    </tr>
    <tr>
    <th colspan="4" class="header3" style="padding:5px;font-size:11px; font-family: verdana;">
        <%= SystemManager.getText("message_from_user")%> <%= m.getFromLogin()%>
    </th>
    </tr>
</table>