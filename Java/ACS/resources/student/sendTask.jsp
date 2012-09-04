<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.constants.ACSConstants,
                 ru.lexx.acsystem.backend.system.SystemManager,
                 ru.lexx.acsystem.backend.task.GivenTasksManager" %>
<%@ page import="ru.lexx.acsystem.backend.task.Task" %>
<%@ page import="ru.lexx.acsystem.backend.task.stat.UserStatistic" %>
<%@ page import="ru.lexx.acsystem.backend.user.UserAccaunt" %>
<%@ page import="ru.lexx.acsystem.backend.utils.ACSStringUtils" %>
<%@ page import="ru.lexx.acsystem.webinterface.HtmlConstants" %>
<%@ page import="ru.jdev.html.forms.FormImpl" %>
<%@ page import="ru.jdev.html.forms.elements.LinkElement" %>
<%@ page import="ru.jdev.html.forms.elements.SubmitElement" %>
<%@ page import="ru.jdev.html.forms.elements.TextareaElement" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="ru.jdev.constants.HtmlConstants"%>
<%
    SimpleRequestContext context = (SimpleRequestContext) request.getAttribute("context");
    if (context.hasFlag("new_partition")) {
        Map mParams = new HashMap();
        mParams.put("message_header", SystemManager.getText("acs2.student.sendtask.new_partition_h"));
        mParams.put("message_body", SystemManager.getText("acs2.student.sendtask.new_partition_b"));
        out.println(ACSStringUtils.processTemplate("message", mParams));
    } else if (context.hasFlag("finish")) {
        UserStatistic stat = (UserStatistic) context.getObject("stat");
        Map mParams = new HashMap();
        mParams.put("message_header", SystemManager.getText("acs2.student.sendtask.finish_h"));
        mParams.put("message_body", SystemManager.getText("acs2.student.sendtask.finish_b") + SystemManager.getText("acs2.student.sendtask.points") + Math.round(stat.getAvgPoints()));
        out.println(ACSStringUtils.processTemplate("message", mParams));

    }
    Task task = ((Task) context.getObject("task"));
    Date gd = GivenTasksManager.getGivenDate((UserAccaunt) request.getSession().getAttribute(ACSConstants.ACCAUNT_ATTRIBUTE_NAME));
    if (task != null && gd != null) {
        Map mParams = new HashMap();
        mParams.put("message_header", SystemManager.getText("your_task"));
        mParams.put("message_body", task.getFormulation());
        out.println(ACSStringUtils.processTemplate("message", mParams));

        FormImpl f = new FormImpl("acsform");
        f.setName("send_program_form");
        f.setFormUserName(SystemManager.getText("send_program_form"));
        f.setAction("index.jsp?page=sendtask&operation=check");

        TextareaElement program = new TextareaElement("prog", context.getNotNullString("prog"), SystemManager.getText("program"));
        program.setCols(80);
        program.setRows(25);
        program.setTabindex(0);
        program.setCheckJs(HtmlConstants.CHECK_NOT_NULL);
        program.setErrorMessage(SystemManager.getText("enter_program"));

        SubmitElement check = new SubmitElement("check", SystemManager.getText("send"));
        int dtr = task.getDays_to_resolve() * 2 / 3;
        Calendar cal = Calendar.getInstance();
        cal.setTime(gd);
        cal.add(Calendar.DAY_OF_MONTH, dtr);
        Calendar cur = Calendar.getInstance();
        LinkElement cancel = null;
        if (cur.after(cal)) {
            cancel = new LinkElement("javascript: location.href=\"index.jsp?page=sendtask&operation=cancel\"", "", "", SystemManager.getText("cancel"));
            cancel.setNotice(SystemManager.getText("cancel_notice"));
        }
        f.addElement(program);
        f.addElement(check);
        if (cur.after(cal))
            f.addElement(cancel);
        out.println(f.getHtmlWithScript());
    }
%>