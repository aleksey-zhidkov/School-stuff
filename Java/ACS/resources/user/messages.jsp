<%@ page import="ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.lexx.acsystem.backend.messenger.Message"%>
<%@ page import="ru.lexx.acsystem.backend.constants.ACSConstants"%>
<%@ page import="ru.lexx.acsystem.webinterface.ACSRequestContext"%>
<%@ page import="ru.jdev.utils.string.StringUtils"%>

<script language="javascript">
    function swap(type) {
        if (type == 1) {
            inh.className = "mes_header_selected";
            input.style.display = "";
            outh.className = "mes_header_n_selected_o";
            output.style.display = "none";
        } else {
            outh.className = "mes_header_selected";
            output.style.display = "";
            inh.className = "mes_header_n_selected_i";
            input.style.display = "none";
        }
    }
</script>
<table width="100%" cellpadding="0"cellspacing="0" style="border:1px solid black">
    <tr>
        <td width="50%" id="inh" onclick="swap(1)" class="mes_header_selected"><%= SystemManager.getText("acs2.user.messages.input")%></td>
        <td width="50%" id="outh" onclick="swap(2)" class="mes_header_n_selected_o"><%= SystemManager.getText("acs2.user.messages.output")%></td>
    </tr>
    <th colspan="2" style="background-color: #f2f4fa; font-weight: normal; padding: 25px;">
        <div id="input">
            <table width="80%" cellpadding="0" cellspacing="0" style="font-size:11px; font-family: verdana;">
                <tr align="center" style="background-color: #778899;">
                    <td style="border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;">&nbsp</td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black"><%= SystemManager.getText("acs2.user.messages.from")%></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black"><%= SystemManager.getText("acs2.user.messages.sentDate")%></td>
                    <th style="border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black" colspan="2"  width="200px">&nbsp;</th>
                </tr>
                <tr></tr>
                <%
                    ACSRequestContext ctx = (ACSRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
                    Message[] in = (Message[]) ctx.getObject("in_messages");
                    for (int i=0; i < in.length; i++) {
%>
                <tr align="center" <%= i % 2 == 0 ? "" : "style=\"background-color: #dbe1e8;\""%>>
                    <td style="border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;" width="20px"><img src="images/<%= in[i].getReadDate() == null ? "new" : "old"%>_letter.gif"></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black"><%= in[i].getFromLogin()%></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black;" width="100px"><%= StringUtils.getFormattedDate(in[i].getSentDate()) %></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black" width="100px"><a href="index.jsp?page=messages&mid=<%= in[i].getHook() %>"><%= SystemManager.getText("delete")%></a></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black;cursor: pointer; text-decoration: underline;" width="100px"><a onclick='javascript: window.open("popup_index.jsp?page=viewmessage&no_status=true&mid=<%= in[i].getHook() %>","message","toolbar=no,height=480, width=640, resizable=yes, menubar=no, scrollbars=yes"); return false;'><%= SystemManager.getText("view")%></a></td>
                </tr>
                <tr></tr>
                <%
                    }
                %>
            </table>

        </div>

        <div id="output" style="display: none;">
            <table width="80%" cellpadding="0" cellspacing="0" style="font-size:11px; font-family: verdana;">
                <tr align="center" style="background-color: #778899;">
                    <td style="border-top:1px solid black;border-bottom:1px solid black;border-left:1px solid black" width="100px"><%= SystemManager.getText("acs2.user.messages.sentDate")%></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black"><%= SystemManager.getText("acs2.user.messages.to")%></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black" width="100px"><%= SystemManager.getText("acs2.user.messages.viewDate")%></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black" width="100px"><%= SystemManager.getText("acs2.user.messages.readDate")%></td>
                    <th style="border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black" colspan="2"  width="200px">&nbsp;</th>
                </tr>
                <tr></tr>
                <%
                    ctx = (ACSRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
                    Message[] mout = (Message[]) ctx.getObject("out_messages");
                    for (int i=0; i < mout.length; i++) {
%>
                <tr align="center" <%= i % 2 == 0 ? "" : "style=\"background-color: #dbe1e8;\""%>>
                    <td style="border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;" width="100px"><%= StringUtils.getFormattedDate(mout[i].getSentDate())%></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black"><%= mout[i].getToLogin()%></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black;" width="100px"><%= mout[i].getViewDate() != null ? StringUtils.getFormattedDate(mout[i].getViewDate()) : SystemManager.getText("no") %></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black;" width="100px"><%= mout[i].getReadDate() != null ? StringUtils.getFormattedDate(mout[i].getReadDate()) : SystemManager.getText("no") %></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black" width="100px"><a href="index.jsp?page=messages&mid=<%= mout[i].getHook() %>"><%= SystemManager.getText("delete")%></a></td>
                    <td style="border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black;cursor: pointer; text-decoration: underline;" width="100px"><a onclick='javascript: window.open("popup_index.jsp?page=viewmessage&no_status=true&mid=<%= mout[i].getHook() %>","message","toolbar=no,height=480, width=640, resizable=yes, menubar=no, scrollbars=yes"); return false;'><%= SystemManager.getText("view")%></a></td>
                </tr>
                <tr></tr>
                <%
                    }
                %>
            </table>
        </div>
    </th>
    <tr >
    <td colspan="2" class="header2">
         <a style="line-height:20px" href="index.jsp?page=writemessage&type=all"><%= SystemManager.getText("acs2.user.messages.write")%></a>
    </td>
    </tr>
</table>