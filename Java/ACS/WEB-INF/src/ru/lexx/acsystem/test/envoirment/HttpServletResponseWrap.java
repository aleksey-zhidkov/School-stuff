package ru.lexx.acsystem.test.envoirment;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 14.10.2005
 * Time: 12:30:26
 * To change this template use File | Settings | File Templates.
 */
public class HttpServletResponseWrap implements HttpServletResponse {
    public String getCharacterEncoding() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getContentType() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ServletOutputStream getOutputStream() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public PrintWriter getWriter() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCharacterEncoding(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setContentLength(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setContentType(String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBufferSize(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getBufferSize() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void flushBuffer() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void resetBuffer() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isCommitted() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLocale(Locale locale) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Locale getLocale() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addCookie(Cookie cookie) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean containsHeader(String s) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String encodeURL(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String encodeRedirectURL(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @deprecated
     */
    public String encodeUrl(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @deprecated
     */
    public String encodeRedirectUrl(String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sendError(int i, String s) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sendError(int i) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void sendRedirect(String s) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDateHeader(String s, long l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addDateHeader(String s, long l) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setHeader(String s, String s1) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addHeader(String s, String s1) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setIntHeader(String s, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addIntHeader(String s, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setStatus(int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @deprecated
     */
    public void setStatus(int i, String s) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
