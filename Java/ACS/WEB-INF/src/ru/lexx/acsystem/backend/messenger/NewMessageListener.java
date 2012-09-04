package ru.lexx.acsystem.backend.messenger;

import ru.lexx.acsystem.backend.user.UserAccaunt;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 28.01.2006
 * Time: 19:42:40
 */
public interface NewMessageListener {

    void newMessage(String text, UserAccaunt from);

}
