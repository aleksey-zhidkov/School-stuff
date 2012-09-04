package ru.lexx.acsystem.test.envoirment;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 21.11.2005
 * Time: 13:16:38
 */
public class ACSTestContextFactory implements InitialContextFactory {
    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException {
        ACSTestContext actx = new ACSTestContext();
        Context sctx = actx.createSubcontext("java:comp/env");
        sctx.bind("CONFIG_PATH", "D:\\lexx\\MW\\Programming\\Java\\ACS\\exploded\\WEB-INF\\config\\acsconfig.xml");

        return actx;
    }
}
