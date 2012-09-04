<%@ page import="ru.jdev.requesthandling.request.SimpleRequestContext,
                 ru.lexx.acsystem.backend.constants.ACSConstants,
                 ru.jdev.html.forms.FormImpl,
                 ru.lexx.acsystem.backend.system.SystemManager"%>
<%
  SimpleRequestContext context = (SimpleRequestContext) request.getAttribute(ACSConstants.CONTEXT_ATTRIBUTE_NAME);
  boolean success = context.getBoolean("success");
  if (context.hasFlag("invalid_pass")) {
    out.println(SystemManager.getText("acs2.user.updateuser.invalid_pass")+"<br/>");
  }
  if (context.hasFlag("invalid_new_pass")) {
    out.println(SystemManager.getText("acs2.user.updateuser.invalid_new_pass")+"<br/>");
  }
  if (context.hasFlag("pass_updated") && success) {
    out.println(SystemManager.getText("acs2.user.updateuser.pass_updated")+"<br/>");
  }
  if (context.hasFlag("invalid_email")) {
    out.println(SystemManager.getText("acs2.user.updateuser.invalid_email")+"<br/>");
  }
  if (context.hasFlag("email_updated") && success) {
    out.println(SystemManager.getText("acs2.user.updateuser.email_updated")+"<br/>");
  }
  if (context.hasFlag("no_updated")) {
    out.println(SystemManager.getText("acs2.user.updateuser.no_updated")+"<br/>");
  }
  if (!(context.hasParameter("success") && success)) {
    FormImpl f = (FormImpl) context.getObject("form");
    out.println(f.getHtmlWithScript());
  }
%>