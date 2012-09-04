package ru.lexx.acsystem.webinterface.phandlers.user;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.messenger.Message;
import ru.lexx.acsystem.backend.messenger.Messenger;
import ru.lexx.acsystem.backend.user.RightsType;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: malinka
 * Date: 31.01.2006
 * Time: 22:02:30
 * To change this template use File | Settings | File Templates.
 */
public class MessagesPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        UserAccaunt accaunt = (UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME);
        if (context.hasParameter("mid"))
            delete((ACSRequestContext) context);        
        Message[] in = Messenger.getInMessages(accaunt);
        if (accaunt.getSys_group().getRights().contains(RightsType.RIGHTS_ADMIN)) {
            Message[] ain = Messenger.getInMessages(UserManager.TESH_SUPPORT);
            Message[] res = new Message[in.length + ain.length];
            System.arraycopy(in, 0, res, 0, in.length);
            System.arraycopy(ain, 0, res, in.length, ain.length);
            in = res;
        }
        Message[] out = Messenger.getOutMessages(accaunt);
        context.setObject("in_messages", in);
        context.setObject("out_messages", out);
        accaunt.setNewMessages(0);
    }

    private void delete(ACSRequestContext context) {
        int del = Messenger.deleteMessage(context.getInt("mid"));
        if (del > 0)
            context.addMessage(new StatusMessage("Сообщение удалено", StatusMessageType.OK_MESSAGE));
        else
            context.addMessage(new StatusMessage("Сообщение не удалено, попробуйте позже", StatusMessageType.ERROR_MESSAGE));
    }

    public void destroy() {
    }
}
