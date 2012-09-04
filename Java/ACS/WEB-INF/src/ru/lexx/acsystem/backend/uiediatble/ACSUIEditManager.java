package ru.lexx.acsystem.backend.uiediatble;

import org.xml.sax.SAXException;
import ru.jdev.utils.xml.XMLUtilsException;
import ru.jdev.utils.xml.XmlUtils;
import ru.lexx.acsystem.backend.system.SystemManager;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 23.02.2006
 * Time: 16:58:05
 */
public class ACSUIEditManager {

    private static HashMap<String, ACSUIEditor> editors = new HashMap<String, ACSUIEditor>();

    static {
        load();
    }

    public static ACSUIEditor getEditor(String type) {
        return editors.get(type);
    }

    public static synchronized void load() {
        try {
            editors = (HashMap<String, ACSUIEditor>) XmlUtils.getMap(SystemManager.getProperty(SystemManager.ACS_BASE_DIR) + "WEB-INF\\config\\uieditors.xml",
                                                                     "ui-editors/editor",
                                                                     new ACSUIEntryFactory());
        } catch (ParserConfigurationException e) {
            SystemManager.getLogger().log(ACSUIEditManager.class.getName() + ".static", e);
        } catch (IOException e) {
            SystemManager.getLogger().log(ACSUIEditManager.class.getName() + ".static", e);
        } catch (SAXException e) {
            SystemManager.getLogger().log(ACSUIEditManager.class.getName() + ".static", e);
        } catch (XPathExpressionException e) {
            SystemManager.getLogger().log(ACSUIEditManager.class.getName() + ".static", e);
        } catch(XMLUtilsException e) {
            SystemManager.getLogger().log(ACSUIEditManager.class.getName() + ".static", e);
            e.printStackTrace();
            SystemManager.setIsSystemFail(true);
        }
    }

    public static synchronized void reload() {
        editors.clear();
        load();
    }

}
