package ru.lexx.acsystem.test.backend.uieditable;

import junit.framework.TestCase;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditor;
import ru.lexx.acsystem.backend.uiediatble.ACSUIEditManager;
import ru.lexx.acsystem.backend.task.Task;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.site.NewsItem;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.lexx.acsystem.backend.system.SystemLoader;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 29.05.2006
 * Time: 22:34:01
 */
public class ACSUIEditManagerTest extends TestCase {

    static {
        SystemLoader sl = new SystemLoader();
    }

    public ACSUIEditManagerTest() {
    }

    public ACSUIEditManagerTest(String string) {
        super(string);
    }

    public void runTest() {
        ACSUIEditor editor = ACSUIEditManager.getEditor("task");
        assertNotNull(editor);
        assertTrue(editor.getEditable() instanceof Task);
        editor = ACSUIEditManager.getEditor("user");
        assertNotNull(editor);
        assertTrue(editor.getEditable() instanceof UserAccaunt);
        editor = ACSUIEditManager.getEditor("news");
        assertNotNull(editor);
        assertTrue(editor.getEditable() instanceof NewsItem);
    }

}
