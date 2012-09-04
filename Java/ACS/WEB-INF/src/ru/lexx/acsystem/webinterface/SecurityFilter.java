package ru.lexx.acsystem.webinterface;

import ru.jdev.utils.logging.LogMessageLevel;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.site.MenuManager;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 27.11.2005
 * Time: 14:36:43
 */
public class SecurityFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest)) {
            // ���� �� ���� ������� - �� ���� �� ������
            filterChain.doFilter(request, response);
            return;
        }
        HttpServletRequest hr = (HttpServletRequest) request;        
        String path = hr.getServletPath();
        String page = hr.getParameter("page");
        if ("error_message.jsp".equalsIgnoreCase(path) ||
            "accessdenied".equalsIgnoreCase(page) ||
            "logoff".equalsIgnoreCase(page)) {
            // ��� ���������� ������)))
            filterChain.doFilter(hr, response);
            return;
        }
        UserAccaunt accaunt = (UserAccaunt) hr.getSession().getAttribute("accaunt");
        if (accaunt == null)
            accaunt = UserManager.noneAccaunt;
        if (path.equalsIgnoreCase("/index.jsp")) {
            if (page != null && !MenuManager.haveAccess(accaunt.getSys_group().getRights(), page)) {
                /* ���� � ������������ �� ���������� ���� ��� ��������� ����������� ��������
                   �� ������������� ��� �� ����� �����, �� ����������, ���� �� �����,
                   �� ������ ��������� ������ - ����� ������������� �� ����������� ��������
                */
                hr.getSession().setAttribute("requested_page", page);
                hr.getSession().setAttribute("other_params", getParameters(hr));
                SystemManager.getLogger().log("Request for " + path + " are redirected", LogMessageLevel.LEVEL_WARNING, getClass().getName() + "doFilter");
                ((HttpServletResponse) response).sendRedirect(SystemManager.getProperty("SERVER_URL") + "index.jsp?page=enter");
                return;
            }
        } else if (path.equalsIgnoreCase("/popup_index.jsp")) {
            if (page == null || !MenuManager.haveAccess(accaunt.getSys_group().getRights(), page)) {
                SystemManager.getLogger().log("Request for " + path + " are redirected", LogMessageLevel.LEVEL_WARNING, getClass().getName() + "doFilter");
                ((HttpServletResponse) response).sendRedirect(SystemManager.getProperty("SERVER_URL") + "index.jsp?page=accessdenied");
                return;
            }
        } else {
            // ���� ��������� ��������� �� ������, � ����� index.jsp - �������������
            SystemManager.getLogger().log("Request for " + path + " are redirected", LogMessageLevel.LEVEL_WARNING, getClass().getName() + "doFilter");
            ((HttpServletResponse) response).sendRedirect(SystemManager.getProperty("SERVER_URL") + "index.jsp");
            return;
        }
        filterChain.doFilter(request, response);
    }

    public void destroy() {
    }

    /**
     * ��������� ������ GET-����������. ���������� ��� ��������������� ����� �����
     * ��������� ������. �������� page �� �����������
     *
     * @param request
     * @return ������ � ���� &�����1=�������1&�����2=��������2&...
     */
    private static String getParameters(HttpServletRequest request) {
        StringBuffer res = new StringBuffer("&");
        Enumeration en = request.getParameterNames();
        for (; en.hasMoreElements();) {
            String name = (String) en.nextElement();
            if ("page".equalsIgnoreCase(name))
                continue;
            String value = request.getParameter(name);
            res.append(name);
            res.append('=');
            res.append(value);
            res.append('&');
        }
        return res.toString();
    }
}
