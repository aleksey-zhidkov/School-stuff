package ru.lexx.acsystem.backend.system;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class SystemLoader extends GenericServlet {

    static {
        try {
            SystemManager.load();
        }
        catch (Exception e) {
            e.printStackTrace();
            SystemManager.setIsSystemFail(true);
        }
    }

    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
    }
}
