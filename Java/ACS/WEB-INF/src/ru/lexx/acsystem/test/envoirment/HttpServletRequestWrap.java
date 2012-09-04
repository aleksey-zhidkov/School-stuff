package ru.lexx.acsystem.test.envoirment;

import ru.jdev.common.EnumerationImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 14.10.2005
 * Time: 12:18:01
 * To change this template use File | Settings | File Templates.
 */
public class HttpServletRequestWrap implements HttpServletRequest {

    private HashMap reqParams = new HashMap();

    public String getAuthType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Cookie[] getCookies() {
        return new Cookie[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getDateHeader(String s) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getHeader(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Enumeration getHeaders(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Enumeration getHeaderNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getIntHeader(String s) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getMethod() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getPathInfo() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getPathTranslated() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getContextPath() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getQueryString() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRemoteUser() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isUserInRole(String s) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Principal getUserPrincipal() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRequestedSessionId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRequestURI() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public StringBuffer getRequestURL() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getServletPath() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HttpSession getSession(boolean b) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public HttpSession getSession() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isRequestedSessionIdValid() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isRequestedSessionIdFromCookie() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isRequestedSessionIdFromURL() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @deprecated
     */
    public boolean isRequestedSessionIdFromUrl() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getAttribute(String s) {
        return null;
    }

    public Enumeration getAttributeNames() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getCharacterEncoding() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCharacterEncoding(String s) throws UnsupportedEncodingException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getContentLength() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getContentType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServletInputStream getInputStream() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getParameter(String s) {
        return (String) reqParams.get(s);
    }

    public Enumeration getParameterNames() {
        return new EnumerationImpl(reqParams.keySet().iterator());
    }

    public String[] getParameterValues(String s) {
        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map getParameterMap() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getProtocol() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getScheme() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getServerName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getServerPort() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BufferedReader getReader() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRemoteAddr() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getRemoteHost() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAttribute(String s, Object o) {
        reqParams.put(s, o);
    }

    public void removeAttribute(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Locale getLocale() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Enumeration getLocales() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isSecure() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @deprecated
     */
    public String getRealPath(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getRemotePort() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getLocalName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getLocalAddr() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getLocalPort() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
