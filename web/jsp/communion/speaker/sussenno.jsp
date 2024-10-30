<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	String hint = (String)request.getAttribute("hint");
	if(hint != null && !hint.equals("")) {
		out.println(hint);
	}
	%>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/speaker.do?cmd=n1")%>" method="get"></go>继续发言</anchor><br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
