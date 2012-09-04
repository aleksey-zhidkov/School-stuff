package ru.lexx.acsystem.backend.uiediatble;

import ru.jdev.utils.xml.EntryFactory;
import ru.jdev.utils.xml.Entry;
import ru.jdev.utils.xml.XmlUtils;
import ru.jdev.utils.xml.XMLUtilsException;
import org.w3c.dom.Node;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 29.05.2006
 * Time: 22:48:53
 */
public class ACSUIEntryFactory implements EntryFactory {
    public Entry build(Node n) {
        ACSUIEditor editor;
        try {
            editor = Class.forName(XmlUtils.getChaildNodeText(n, "class")).asSubclass(ACSUIEditor.class).newInstance();
            Entry e = new Entry();
            e.key = editor.getType();
            e.value = editor;
            return e;
        } catch (Exception e) {
            throw new XMLUtilsException(e);
        }
    }
}
