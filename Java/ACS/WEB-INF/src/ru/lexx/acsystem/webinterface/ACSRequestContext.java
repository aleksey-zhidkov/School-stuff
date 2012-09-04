package ru.lexx.acsystem.webinterface;

import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.site.StatusMessage;

import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: jdev
 * Date: 02.06.2006
 * Time: 21:51:56
 */
public class ACSRequestContext extends SimpleRequestContext implements Map {

    private ArrayList<StatusMessage> messages = new ArrayList<StatusMessage>();

        public int size() {
        return params.size();
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }

    public boolean containsKey(Object key) {
        return params.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return params.containsValue(value);
    }

    public Object get(Object key) {
        return params.get(key);
    }

    public Object put(Object key, Object value) {
        return params.put(key, value);
    }

    public Object remove(Object key) {
        return params.remove(key);
    }

    public void putAll(Map t) {
        params.putAll(t);
    }

    public void clear() {
        params.clear();
    }

    public Set keySet() {
        return params.keySet();
    }

    public Collection values() {
        return params.values();
    }

    public Set entrySet() {
        return params.entrySet();
    }

    public void addMessage(StatusMessage mes) {
        messages.add(mes);
    }

    public ArrayList<StatusMessage> getMessages() {
        return messages;
    }
}
