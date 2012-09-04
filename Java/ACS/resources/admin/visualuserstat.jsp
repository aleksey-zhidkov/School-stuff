<%@ page import="ru.lexx.acsystem.backend.system.SystemManager" %>
<%@ page import="ru.lexx.acsystem.backend.task.TaskPartition" %>
<%@ page import="ru.lexx.acsystem.backend.task.TaskPartitionsManager" %>
<%@ page import="ru.lexx.acsystem.backend.task.stat.ColorMapper" %>
<%@ page import="ru.lexx.acsystem.backend.task.stat.GStatUnit" %>
<%@ page import="ru.lexx.acsystem.backend.user.RightsType" %>
<%@ page import="ru.lexx.acsystem.backend.user.UserAccaunt" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.util.ArrayList" %>
<%
    SimpleRequestContext src = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
    int max_prs = src.getInt("max_prs");
    if (max_prs == 0) {
        return;
    }
    ColorMapper mapper = new ColorMapper(max_prs);
    TaskPartition[] allPartitions = TaskPartitionsManager.getAllPartition();
%>
<table cellpadding="5" cellspacing="0">
    <tr>
        <%
            if (((UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME)).getSys_group().getRights().contains(RightsType.RIGHTS_ADMIN)) {
        %>
        <td>
            <%@ include file="selectuser.jsp" %>
        </td>
        <%    }
        %>
        <td valign="top">
            <div style="width: 250px; background-color:  #f2f4fa; border: 1px solid black">
                <div style="text-align: right; line-height:14px; font-size: 12px; padding:5px;" class="header2">
                    <%= SystemManager.getText("acs2.admin.visualuserstat.legend")%>
                </div>
                <ol style="margin-left: 15px">
                    <%
                        for (int i = 0; i < max_prs; i++) {
                            Color c = mapper. getColor(i);
                            String color = "color=0x" + ((c.getRed() > 9) ? Integer.toString(c.getRed(), 16) : "0" + Integer.toString(c.getRed(), 16)) +
                                           ((c.getGreen() > 9) ? Integer.toString(c.getGreen(), 16) : "0" + Integer.toString(c.getGreen(), 16)) +
                                           ((c.getBlue() > 9) ? Integer.toString(c.getBlue(), 16) : "0" + Integer.toString(c.getBlue(), 16));
                            out.println("<li><img src=\"images/usvisualizer?points=0&" + color + "\"> - " + allPartitions[i].getName());
                        }
                    %>
                </ol>

                <div style="text-align: right; line-height:14px; font-size: 12px; padding:5px;" class="header2">
                    <%= SystemManager.getText("acs2.admin.visualuserstat.legend")%>
                </div>
            </div>
        </td>
    </tr>
</table>
<br><br>
<table width="80%" cellpadding="1px" cellspacing="0px" style="background-color: #f2f4fa; border: 1px solid black">
    <tr>
        <td colspan="2"
            style="text-align: right; line-height:14px; font-size: 12px; padding:5px;background-color:  #E9EAED; border-top: 0px solid black"
            class="header2"><%= SystemManager.getText("acs2.user.stat.graphstat")%></td>
    </tr>
    <%
        UserAccaunt[] users = (UserAccaunt[]) src.getObject("users");
        int page_list = src.getInt("page_list");
        if (page_list == 0)
            page_list = 1;
        int from = (page_list - 1) * 10;
        int to = from + 10 < users.length ? from + 10 : users.length;
        for (int i = from; i < to; i++) {
            out.println("<tr " + (i % 2 == 0 ? "" : "style=\"background-color: #dbe1e8;\"") + ">");
            if (src.hasParameter("admin")) {
            out.println("<td style=\"border-bottom: 1px solid black;\" width=\"300px\">" +
                        SystemManager.getText("acs2.admin.visualstat.users_stat") + users[i].getLogin() +
                        "</td>");
            }
            out.println("<td style=\"border-bottom: 1px solid black;\">");
            GStatUnit[] stat = (GStatUnit[]) src.getObject("stat" + i);
            ArrayList prs = new ArrayList();
            String curPrs = "";
            for (int j = 0; j < stat.length; j++) {
                if (!curPrs.equals(stat[j].getPartition())) {
                    curPrs = stat[j].getPartition();
                    prs.add(new ArrayList());
                }
                ((ArrayList) prs.get(prs.size() - 1)).add(new Integer((int) stat[j].getPoints()));
            }
            for (int j = 0; j < max_prs; j++) {
                StringBuffer row;
                if (j < prs.size()) {
                    row = new StringBuffer("<img src=\"images/usvisualizer?points=");
                    ArrayList points = (ArrayList) prs.get(j);
                    for (int k = 0; k < points.size(); k++) {
                        row.append(points.get(k) + ",");
                    }
                    row.setCharAt(row.length() - 1, '&');
                    Color c = mapper.getColor(j);
                    String color = "color=0x" + ((c.getRed() > 9) ? Integer.toString(c.getRed(), 16) : "0" + Integer.toString(c.getRed(), 16)) +
                                   ((c.getGreen() > 9) ? Integer.toString(c.getGreen(), 16) : "0" + Integer.toString(c.getGreen(), 16)) +
                                   ((c.getBlue() > 10) ? Integer.toString(c.getBlue(), 16) : "0" + Integer.toString(c.getBlue(), 16));
                    row.append(color);
                    points.clear();
                    row.append("\">");
                } else {
                    row = new StringBuffer("&nbsp;");
                }
                out.println(row.toString());

            }
            out.println("</td></tr>");
        }
    %>
    <tr>
        <td colspan="2"
            style="border: 0px solid black; text-align: right; line-height:14px; font-size: 12px; padding:5px;background-color:  #E9EAED"
            class="header2"><%= SystemManager.getText("acs2.user.stat.graphstat")%></td>
    </tr>
</table>
<br><br>

<div>
    <%
        int pages = (int) Math.ceil((double) users.length / 10);
        if (pages == 1)
            pages = 0;
        for (int i = 1; i <= pages; i++) {
            out.println("<a href=\"index.jsp?admin=true&page=visualuserstat&page_list=" + i + "&gid=" + src.getInt("gid") + "\">" + i + "</a>");
        }
    %>
</div>
