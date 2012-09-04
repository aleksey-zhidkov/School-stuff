<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.site.MenuItem,
                 ru.lexx.acsystem.backend.system.SystemManager,
                 ru.lexx.acsystem.backend.user.UserManager" %>
<%@ page import="ru.lexx.acsystem.backend.user.RightsType" %>
<%@ page import="ru.lexx.acsystem.backend.user.UserAccaunt" %>
<img src="images/nav.gif"><img src="images/all.gif">
<%
    SimpleRequestContext ctx = (SimpleRequestContext) request.getAttribute("context");
    UserAccaunt accaunt = (UserAccaunt) request.getSession().getAttribute("accaunt");
    MenuItem[] menu = (MenuItem[]) ctx.getObject("none_menu");
    for (int i = 0; i < menu.length; i++) {
%> <a href="<%= menu[i].getUrl()%>"><img border=0px src="images/point.gif"><%= menu[i].getName() %></a><br><%
    }
    if (accaunt.getSys_group().getRights().contains(RightsType.RIGHTS_REGISTERED)) {
%>
<br>
<img src="images/reg.gif">
<%
    menu = (MenuItem[]) ctx.getObject("reg_menu");
    for (int i = 0; i < menu.length; i++) {
%> <a href="<%= menu[i].getUrl()%>"><img border=0px src="images/point.gif"><%= menu[i].getName() %><%
        if (menu[i].getUrl().indexOf("messages") != -1) {
            int nm = accaunt.isNewMessages();
            if (nm > 0)
               out.println(" ("+ nm + " " + (nm == 1 ? SystemManager.getText("acs2.all.menu.snew") :
                                                SystemManager.getText("acs2.all.menu.mnew")));
            }
            out.println("</a><br>");
        }
    }
    if (accaunt.getSys_group().getRights().contains(RightsType.RIGHTS_USER)) {
%>
    <br>
    <img src="images/users.gif">
    <%
    menu = (MenuItem[]) ctx.getObject("user_menu");
    for (int i = 0; i < menu.length; i++) {
%> <a href="<%= menu[i].getUrl()%>"><img border=0px src="images/point.gif"><%= menu[i].getName() %></a><br><%
        }
    }
    if (accaunt.getSys_group().getRights().contains(RightsType.RIGHTS_ADMIN)) {
%>
    <br>
    <img src="images/admin.gif">
    <%
    menu = (MenuItem[]) ctx.getObject("admin_menu");
    for (int i = 0; i < menu.length; i++) {
%> <a href="<%= menu[i].getUrl()%>"><img border=0px src="images/point.gif"><%= menu[i].getName() %></a><br> <%
        }
    }
%>