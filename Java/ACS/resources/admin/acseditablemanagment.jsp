<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.constants.ACSConstants,
                 ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.lexx.acsystem.backend.uiediatble.ACSUIEditable" %>
<%@ page import="ru.lexx.acsystem.backend.uiediatble.ACSUIEditor" %>
<%@ page import="ru.lexx.acsystem.backend.uiediatble.ACSUIFilter" %>
<%@ page import="ru.jdev.html.forms.FormImpl" %>
<%@ page import="ru.jdev.html.forms.elements.HtmlFormInputElement" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    SimpleRequestContext src = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
    ACSUIEditor editor = (ACSUIEditor) src.getObject(ACSConstants.ACSUIEDITOR_ATTRIBUTE_NAME);
    ACSUIFilter filter;
    int page_list = src.getInt("page_list");
    if (page_list == 0)
        page_list = 1;
    String pageName = SystemManager.getProperty("SERVER_URL") + request.getServletPath();
    if (editor instanceof ACSUIFilter) {
        filter = (ACSUIFilter) editor;
        FormImpl f = filter.getFilterForm();
        f.setAction(pageName+"?page=acseditablemanag&type="+editor.getType());
        ((HtmlFormInputElement) f.getElementByName("filter")).setValue(src.getString("filter"));
        out.println(f.getHtmlWithScript());
    }

%>
<a href="<%= pageName%>?page=addacseditable&type=<%= editor.getType()%><%= src.hasParameter("tid") ? "&tid="+src.getString("tid") : ""%>"><%= SystemManager.getText("add") %> <%= editor.getEditable().getEditableName2() %></a>
<table style="padding: 5px; font-size:11px; font-family: verdana; background-color: #f2f4fa;" cellpadding="0" cellspacing="0" width="85%">
    <tr align="center" style="background-color: #778899;">
        <%
            String[] head = editor.getColNames();
            for (int i = 0; i < head.length; i++) {
                String style = "";
                if (i == 0)
                    style = "style=\"border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black;\"";
                else
                    style = "style=\"border-top:1px solid black;border-bottom:1px solid black;\"";
        %>
        <td <%= style%>>
            <%= head[i] %>
        </td>
        <%
            }
        %>
        <th style="border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black" colspan="2" align="center">
            <%= SystemManager.getText("acs2.admin.acseditablemanagment.actions")%>
        </th>
    </tr>
    <%
        ACSUIEditable[] editables;
        if (src.hasParameter("filter") && editor instanceof ACSUIFilter)
            editables = ((ACSUIFilter) editor).getFilteredEditables(src);
        else
            editables = editor.getEditables(src);
        int from = (page_list - 1) * 10;
        int to = from + 10 < editables.length ? from + 10 : editables.length;
        for (int i = from; i < to; i++) {
            try {
                out.println("<tr align=\"center\" "+ ((i % 2 == 0) ? ("style=\"line-height: 25px;\"") : ("style=\"background-color: #dbe1e8; line-height: 25px;\""))+">");
                String[] body = editables[i].toStringArray();
                for (int j = 0; j < (body.length > 5 ? 5 : body.length); j++) {
                    String style = "";
                    if (j == 0)
                        style= "style=\"border-top:1px solid black;border-bottom:1px solid black; border-left:1px solid black\"";
                    else
                        style= "style=\"border-top:1px solid black;border-bottom:1px solid black;\"";
                    out.println("  <td "+ style +">");
                    String str = body[j];
                    if (str == null || str.length() == 0)
                        str = "&nbsp";
                    if (str.length() > 9)
                        str = str.substring(0, 9) + "...";
                    out.println(str);
                    out.println("  </td>");
                }
                out.println("  <td style=\"border-top:1px solid black;border-bottom:1px solid black;\">");
                out.println("<a href='" + pageName + "?page=acseditablemanag&action=delete&id=" + editables[i].getId() + "&type=" + editor.getType() + ("task_data".equalsIgnoreCase(editor.getType()) ? "&tid=" + src.getInt("tid") : "") + "'>" +
                            SystemManager.getText("acs2.admin.usermanagment.delete") + "</a>");
                out.println("  </td>");
                out.println("  <td style=\"border-top:1px solid black;border-bottom:1px solid black; border-right:1px solid black\">");
                out.println("<a href='" + pageName + "?page=updateacseditable&id=" + editables[i].getId() + "&type=" + editor.getType() + ("task_data".equalsIgnoreCase(editor.getType()) ? "&tid=" + src.getInt("tid") : "") + "'>" +
                            SystemManager.getText("acs2.admin.usermanagment.update") + "</a>");
                out.println("  </td>");
                out.println("</tr>");
            } catch (Exception e) {
                out.println("<th colspan=" + (editables.length + 2) + ">Incorrect row in table</th>");
                SystemManager.getLogger().log("admin/acseditablemanagment.jsp", e);
            }
        }
    %>
</table>
<div>
    <%
        int pages = (int) Math.ceil((double) editables.length / 10);
        String gr = "";
        if (src.hasParameter("group"))
            gr = "&group=" + src.getString("group");
        if (pages == 1)
            pages = 0;
        for (int i = 1; i <= pages; i++) {
            out.println("<a href=\"" + request.getContextPath() + "?page=acseditablemanag&type=" + editor.getType() + "&page_list=" + i + gr + (src.hasParameter("filter") ? "&filter="+src.getString("filter") : "") + "\">" + i + "</a>");
        }
    %>
</div>
<a href="<%= pageName%>?page=addacseditable&type=<%= editor.getType()%><%= src.hasParameter("tid") ? "&tid="+src.getString("tid") : ""%>"><%= SystemManager.getText("add") %> <%= editor.getEditable().getEditableName2() %></a>