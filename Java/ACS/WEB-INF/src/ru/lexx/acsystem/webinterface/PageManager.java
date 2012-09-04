package ru.lexx.acsystem.webinterface;

import ru.jdev.utils.xml.XmlUtils;
import ru.jdev.utils.xml.SimpleEntryFactory;
import ru.lexx.acsystem.backend.system.SystemManager;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 29.05.2006
 * Time: 22:05:34
 */
public class PageManager {

    private static Map pages;

    static {
        try {
        pages = XmlUtils.getMap(SystemManager.getProperty(SystemManager.ACS_BASE_DIR) + "WEB-INF\\config\\pages.xml",
                                "acspages/page", new SimpleEntryFactory("name","file"));
        } catch(Exception e) {
            SystemManager.getLogger().log(PageManager.class.getName()+".static",e);
            SystemManager.setIsSystemFail(true);
        }
    }

    public static String getPage(String page) {
        return (String) pages.get(page);
    }
}
