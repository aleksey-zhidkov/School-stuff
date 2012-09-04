<%@ page import="ru.lexx.acsystem.backend.system.SystemManager" %>

<%@ page contentType="text/html; charset=utf-8" %>
<%
    Exception e = pageContext.getException();
    SystemManager.getLogger().log("errorPage[!TOTAL_FAIL!]", e);
    if ("error_message".equalsIgnoreCase(request.getParameter("page"))) {
        SystemManager.setIsSystemFail(true);
    }
    if (SystemManager.isSystemFail()) {
        response.sendRedirect("total_fail.htm");
    } else {
        response.sendRedirect("index.jsp?page=error_message");
    }
%>