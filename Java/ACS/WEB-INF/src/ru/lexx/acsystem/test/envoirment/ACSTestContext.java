package ru.lexx.acsystem.test.envoirment;

import javax.naming.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Alexey
 * Date: 21.11.2005
 * Time: 14:06:43
 */
public class ACSTestContext implements Context {
    private Map ctx = new HashMap();

    public Object lookup(Name name) throws NamingException {
        return ctx.get(name.toString());
    }

    public Object lookup(String name) throws NamingException {
        return ctx.get(name);
    }

    public void bind(Name name, Object obj) throws NamingException {
        ctx.put(name.toString(), obj);
    }

    public void bind(String name, Object obj) throws NamingException {
        ctx.put(name, obj);
    }

    public void rebind(Name name, Object obj) throws NamingException {
        ctx.put(name.toString(), obj);
    }

    public void rebind(String name, Object obj) throws NamingException {
        ctx.put(name, obj);
    }

    public void unbind(Name name) throws NamingException {
        ctx.remove(name.toString());
    }

    public void unbind(String name) throws NamingException {
        ctx.remove(name);
    }

    public void rename(Name oldName, Name newName) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void rename(String oldName, String newName) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void destroySubcontext(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void destroySubcontext(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Context createSubcontext(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Context createSubcontext(String name) throws NamingException {
        ACSTestContext sctx = new ACSTestContext();
        ctx.put(name, sctx);
        return sctx;
    }

    public Object lookupLink(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Object lookupLink(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NameParser getNameParser(Name name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public NameParser getNameParser(String name) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Name composeName(Name name, Name prefix) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public String composeName(String name, String prefix) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Object removeFromEnvironment(String propName) throws NamingException {
        throw new UnsupportedOperationException();
    }

    public Hashtable<?, ?> getEnvironment() throws NamingException {
        throw new UnsupportedOperationException();
    }

    public void close() throws NamingException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getNameInNamespace() throws NamingException {
        throw new UnsupportedOperationException();
    }
}
