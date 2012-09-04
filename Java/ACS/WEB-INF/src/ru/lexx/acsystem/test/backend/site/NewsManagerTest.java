package ru.lexx.acsystem.test.backend.site;

import junit.framework.TestCase;
import ru.lexx.acsystem.backend.site.NewsItem;
import ru.lexx.acsystem.backend.site.NewsManager;
import ru.lexx.acsystem.backend.system.SystemLoader;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 18.11.2005
 * Time: 0:16:55
 */
public class NewsManagerTest extends TestCase {
    public NewsManagerTest() {
    }

    public NewsManagerTest(String s) {
        super(s);
        SystemLoader l = new SystemLoader();
    }

    public void testOperations() {
        int initLen = NewsManager.getLastNews(1000).length;
        NewsItem item = NewsManager.createNews("News text", null);
        NewsItem[] ni = NewsManager.getLastNews(1000);
        assertTrue("News text".equalsIgnoreCase(ni[0].getText()));
        assertTrue(initLen + 1 == ni.length);
        NewsManager.deleteNews(item.getHook());
        int aftLen = NewsManager.getLastNews(1000).length;
        assertTrue(initLen == aftLen);
    }

    public void runTest() {
        testOperations();
    }
}
