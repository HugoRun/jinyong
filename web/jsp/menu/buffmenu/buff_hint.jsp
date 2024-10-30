<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.web.service.player.*,com.ls.model.user.*" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(request.getSession());
		String hint = (String) request.getAttribute("hint");
		String return_type = (String) request.getAttribute("return_type");
		if (hint != null && !hint.equals("")) {
	%>
	<%=hint%><br/>
	<%
		}
		%>
	
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
