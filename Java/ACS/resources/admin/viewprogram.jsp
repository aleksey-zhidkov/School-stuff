<%@ page import="ru.lexx.acsystem.backend.constants.ACSConstants" %>
<%@ page import="ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.lexx.acsystem.backend.task.stat.RecivedProgram" %>
<%@ page import="ru.lexx.acsystem.webinterface.ACSRequestContext" %>
<%@ page import="ru.jdev.utils.string.Converter"%>
<%
    RecivedProgram rp = (RecivedProgram) ((ACSRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME)).getObject("program");
%>
<br>
<table align="center" width="75%" style="border:1px solid black;font-size:11px; font-family: verdana;" cellpadding="0"
       cellspacing="0">
    <tr>
        <th colspan="4" class="header3" style="padding:5px">
            <%= SystemManager.getText("program")%>
        </th>
    </tr>
    <tr>
    </tr>
    <tr>
        <th colspan="4" class="text" align="justify"
            style="font-weight: normal; border-top: 1px solid black;background-color: #f2f4fa; padding: 20px; padding:5px; font-size:13px;">
            <code>
                <%= Converter.getHTMLString(rp.getProgram()) %>
            </code>
        </th>
    </tr>
    <tr>
        <th colspan="4" class="header3" style="padding:5px;font-size:11px; font-family: verdana;">
            <%= SystemManager.getText("program")%>
        </th>
    </tr>
</table>