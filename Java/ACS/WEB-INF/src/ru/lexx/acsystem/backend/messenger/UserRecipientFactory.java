package ru.lexx.acsystem.backend.messenger;

import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;

/**
 * Created by IntelliJ IDEA.
 * User: malinka
 * Date: 05.02.2006
 * Time: 23:08:14
 * To change this template use File | Settings | File Templates.
 */
public class UserRecipientFactory implements RecipientFactory {

    private String login;

    public UserRecipientFactory(String _login) {
        login = _login;
    }

    public UserAccaunt[] getRecipients() {
        return new UserAccaunt[]{UserManager.getAccaunt(login)};
    }
}
