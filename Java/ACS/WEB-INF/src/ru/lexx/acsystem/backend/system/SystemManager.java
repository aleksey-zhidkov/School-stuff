package ru.lexx.acsystem.backend.system;

import com.sun.org.apache.xml.internal.dtm.ref.DTMNodeList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import ru.jdev.utils.db.ConnectionManager;
import ru.jdev.utils.logging.FileLogger;
import ru.jdev.utils.logging.LogMessageLevel;
import ru.jdev.utils.logging.Logger;
import ru.jdev.utils.string.StringUtils;
import ru.jdev.utils.xml.XmlUtils;
import ru.lexx.acsystem.backend.site.MenuManager;
import ru.lexx.acsystem.backend.site.NewsManager;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class SystemManager {
    private static Properties SYSTEM_PROPS;
    private static Properties TEXT;
    private static Logger logger;
    // флаг крушения системы
    private static boolean isSystemFail;

    public static final String ACS_BASE_DIR = "ACS_BASE_DIR";

    static void load() {
        try {
            // загрузка пути к конфигу через jndi. Путь задаётся в web.xml
            InitialContext initctx = new InitialContext();
            Context ctx = (Context) initctx.lookup("java:comp/env");
            String cfg = (String) ctx.lookup("CONFIG_PATH");
            TEXT = new Properties();
            SYSTEM_PROPS = new Properties();
            loadParams(SYSTEM_PROPS, cfg);
            FileInputStream in = new FileInputStream(SYSTEM_PROPS.getProperty(ACS_BASE_DIR) + "WEB-INF\\config\\Text.xml");
            TEXT.loadFromXML(in);
            in.close();
            loadLogger();
            loadConnectionManager(ctx);
            SystemHookGenerator.initialize();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            isSystemFail = true;
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
            isSystemFail = true;
        } catch (IOException e) {
            e.printStackTrace();
            isSystemFail = true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            isSystemFail = true;
        } catch (XPathExpressionException e) {
            e.printStackTrace();
            isSystemFail = true;
        } catch (SAXException e) {
            e.printStackTrace();
            isSystemFail = true;
        } catch (NamingException e) {
            e.printStackTrace();
            isSystemFail = true;
        } catch (Exception e) {
            e.printStackTrace();
            isSystemFail = true;
        }
    }

    private SystemManager() {
    }

    public static synchronized Logger getLogger() {
        return logger;
    }

    /**
     * @param key Имя свойства
     * @return Возвращяет свойство из файла acsconfig.xml, хронящееся под <code>key</code>.
     *         Если такого свойства нет - <code>null</code>
     */
    public static synchronized String getProperty(String key) {
        return SYSTEM_PROPS.getProperty(key);
    }

    /**
     * @param key Имя текстового ресурса
     * @return Возвращяет текстовый ресурс из файла text.xml, хронящееся под <code>key</code>.
     *         Если такого свойства нет - <code>null</code>
     */
    public static synchronized String getText(String key) {
        return TEXT.getProperty(key);
    }

    static void shutDown() {
        if (TEXT != null) {
            TEXT .clear();
            TEXT = null;
        }
        if (SYSTEM_PROPS != null) {
            SYSTEM_PROPS.clear();
            SYSTEM_PROPS = null;
        }
        if (logger != null) {
            logger.log("Class variables are nulled", LogMessageLevel.LEVEL_INFO, SystemManager.class.getName() + ".shutDown");
            logger.shutDown();
        }
    }

    /**
     * Загрузка конфига системы
     *
     * @param dst      Properties в которые будит загружен конфиг
     * @param src_path путь к конфигурационному файлу (acsconfig.xml)
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws XPathExpressionException
     */
    private static void loadParams(Properties dst, String src_path) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        Document doc = XmlUtils.buildDocument(src_path);
        DTMNodeList lst = XmlUtils.selectNodes(doc, "/acs2-config/parameters/param");
        for (int i = 0; i < lst.getLength(); i++) {
            dst.put(XmlUtils.getChaildNodeText(lst.item(i), "param-name").trim(),
                    XmlUtils.getChaildNodeText(lst.item(i), "param-data").trim());
        }
    }

    public static synchronized boolean isSystemFail() {
        return isSystemFail;
    }

    public static synchronized void setIsSystemFail(boolean isFail) {
        isSystemFail = isFail;
    }

    private static void loadLogger() throws IOException {
        String log = SYSTEM_PROPS.getProperty("LOG_DIR") + "acslog-" + StringUtils.getDateAsMySQLString(Calendar.getInstance()) + ".log";
        String debug = SYSTEM_PROPS.getProperty("LOG_DIR") + "acsdebug-" + StringUtils.getDateAsMySQLString(Calendar.getInstance()) + ".log";
        String sql = SYSTEM_PROPS.getProperty("LOG_DIR") + "acssql-" + StringUtils.getDateAsMySQLString(Calendar.getInstance()) + ".log";
        logger = FileLogger.getInstanse(log);
        ((FileLogger) logger).setLevel(LogMessageLevel.valueOf(getProperty("LOGGER_LEVEL")));
        ((FileLogger) logger).setFile(debug, LogMessageLevel.LEVEL_DEBUG);
        ((FileLogger) logger).setFile(sql, LogMessageLevel.LEVEL_SQL);
    }

    private static void loadConnectionManager(Context ctx) throws NamingException {
        // получение стандартного Tomcat'овского дата сурса. Определено в server.xml       
        DataSource ds = (DataSource) ctx.lookup("jdbc/ACSDB");
        if (!ConnectionManager.isLoaded())
            ConnectionManager.load(ds);
        ConnectionManager.setLogger(logger);
    }

    public static synchronized void reload() {
        logger.log("System reloading...", LogMessageLevel.LEVEL_INFO, SystemManager.class.getName());
        shutDown();
        load();
        MenuManager.reload();
        NewsManager.reload();
        ACSUIEditManager.reload();
    }
}
