package ru.lexx.acsystem.backend.utils;

import ru.jdev.utils.string.StringUtils;
import ru.lexx.acsystem.backend.system.SystemManager;

import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 01.12.2005
 * Time: 1:39:21
 */
public class ACSStringUtils {

    private ACSStringUtils() {
    }

    public static String processTemplate(String template, Map<String, String> params) throws IOException {
        String path = SystemManager.getProperty("ACS_BASE_DIR") +
                      "WEB-INF\\config\\templates\\" + template + ".tpl";
        String tplstr = StringUtils.loadFileToString(path);
        return StringUtils.processTemplateString(tplstr, params);
    }
}
