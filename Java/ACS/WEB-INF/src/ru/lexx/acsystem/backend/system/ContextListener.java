package ru.lexx.acsystem.backend.system;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 14.01.2006
 * Time: 19:20:56
 */
public class ContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        SystemManager.load();
    }

    public void contextDestroyed(ServletContextEvent event) {
        SystemManager.shutDown();
    }
}
