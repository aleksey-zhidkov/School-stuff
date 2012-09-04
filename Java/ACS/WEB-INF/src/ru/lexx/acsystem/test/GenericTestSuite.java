package ru.lexx.acsystem.test;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import ru.jdev.utils.string.StringUtils;
import ru.lexx.acsystem.test.envoirment.AbstractTaskTest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Malinka
 * Date: 15.10.2005
 * Time: 12:36:23
 */
public class GenericTestSuite extends TestSuite {
    protected static final String BASE_PATH = "D:\\lexx\\MW\\Programming\\Java\\ACS\\exploded\\WEB-INF\\classes\\";

    protected static TestCase[] findClasses(String classPath) {
        File dir = new File(BASE_PATH + StringUtils.replaceAll(classPath, '.', '\\'));
        ArrayList<TestCase> cases = new ArrayList<TestCase>();
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
                continue;
            StringBuffer fileName = new StringBuffer();
            fileName.append(f.getParent().substring(BASE_PATH.length()));
            fileName.append("\\");
            fileName.append(f.getName());
            fileName.delete(fileName.length() - 6, fileName.length());
            try {
                Class cl = Class.forName(classPath + "." + f.getName().substring(0, f.getName().length() - 6));
                if (cl.getSuperclass() == TestCase.class || cl.getSuperclass() == AbstractTaskTest.class) {
                    cases.add((TestCase) cl.getConstructor(String.class).newInstance(f.getName()));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        TestCase[] tcs = new TestCase[cases.size()];
        cases.toArray(tcs);
        return tcs;
    }
}
