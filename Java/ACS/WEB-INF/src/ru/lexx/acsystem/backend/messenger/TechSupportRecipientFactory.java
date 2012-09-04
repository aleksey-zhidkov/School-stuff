package ru.lexx.acsystem.backend.messenger;

import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 29.01.2006
 * Time: 0:25:40
 */
public class TechSupportRecipientFactory implements RecipientFactory {
    public UserAccaunt[] getRecipients() {
        return new UserAccaunt[]{UserManager.TESH_SUPPORT};
    }
}
