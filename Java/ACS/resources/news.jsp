<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.jdev.utils.string.StringUtils" %>
<%@ page import="ru.lexx.acsystem.backend.site.NewsItem" %>
<%
    SimpleRequestContext ctx = (SimpleRequestContext) request.getAttribute("context");
    NewsItem[] items = (NewsItem[]) ctx.getObject("news");
%>
<img src="images/news.gif"><%for (int i = 0; i < items.length; i++) {%>
<table align=center cellpadding=0 cellspacing=0 width=145px>
    <tr><td height=10px style="background-image: url(images/newsh.gif)">
        <div class=ndata>&nbsp;&nbsp;<%= StringUtils.getFormattedDate(items[i].getDate()) %></div>
    </td></tr>
    <tr><td class=news><%= items[i].getText() %></td></tr>
    <tr><td height=9px style="background-image: url(images/newsf.gif)"></td></tr>
</table>
<br>
<%
    }
%>
 