package ru.lexx.acsystem.webinterface.phandlers.admin;

import ru.jdev.requesthandling.handler.PageHandler;
import ru.jdev.requesthandling.request.RequestContext;
import ru.jdev.requesthandling.request.SimpleRequestContext;
import ru.lexx.acsystem.backend.constants.ACSConstants;
import ru.lexx.acsystem.backend.task.GivenTasksManager;
import ru.lexx.acsystem.backend.task.stat.GStatUnit;
import ru.lexx.acsystem.backend.user.UserAccaunt;
import ru.lexx.acsystem.backend.user.UserManager;
import ru.lexx.acsystem.backend.site.StatusMessage;
import ru.lexx.acsystem.backend.site.StatusMessageType;
import ru.lexx.acsystem.backend.system.SystemManager;
import ru.jdev.html.forms.FormImpl;
import ru.lexx.acsystem.webinterface.ACSRequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Lexx
 * Date: 16.03.2006
 * Time: 1:28:39
 */
public class VisualUserStatPageHandler implements PageHandler {
    public void init(Map params) {
    }

    public void handle(HttpServletRequest request, HttpServletResponse response, RequestContext context) {
        SimpleRequestContext src = (SimpleRequestContext) context;
        FormImpl form = null;
        if (src.hasParameter("uid")) {
            GStatUnit[] stat = GivenTasksManager.getUserStat(src.getInt("uid"));
            src.setObject("stat0", stat);
            src.setInt("max_prs", getPrsCount(stat));
            form = SelectUserPageHandler.getForm("uid", "Пользователь");
            src.setObject("users", new UserAccaunt[]{UserManager.getAccaunt(src.getInt("uid"))});
        } else if (src.hasParameter("gid")) {
            UserAccaunt[] users = UserManager.getUsersbyGroup(src.getInt("gid"));
            int prsCouunt = 0;
            for (int i = 0; i < users.length; i++) {
                GStatUnit[] stat = GivenTasksManager.getUserStat(users[i].getId());
                src.setObject("stat" + i, stat);
                if (getPrsCount(stat) > prsCouunt)
                    prsCouunt = getPrsCount(stat);
                if (stat.length == 0) {
                    src.setObject("stat" + i, new GStatUnit[0]);
                }
            }
            src.setInt("max_prs", prsCouunt);
            if (prsCouunt == 0) {
                ((ACSRequestContext) src).addMessage(new StatusMessage(SystemManager.getText("acs2.admin.stat.no_resolves"), StatusMessageType.INFO_MESSAGE));
            }
            src.setObject("users", users);
            form = SelectGroupPageHandler.getForm("gid", "Группа");
        }
        if (form != null) {
            form.setAction("index.jsp?admin=true&page=visualuserstat");
        }
        context.setObject(ACSConstants.FORM_ATTRIBUTE_NAME, form);
    }

    public static int getPrsCount(GStatUnit[] stat) {
        if (stat.length == 0)
            return 0;
        int count = 1;
        for (int i = 1; i < stat.length; i++) {
            if (!stat[i - 1].getPartition().equalsIgnoreCase(stat[i].getPartition()))
                count++;
        }
        return count;
    }

    public void destroy() {
    }
}
