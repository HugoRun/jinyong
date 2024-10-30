<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.beoffprop.BeOffPropVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String hint = (String) request.getAttribute("hint");
	%>
	<%=hint %><br />
	<anchor>
	<go href="<%=response.encodeURL(GameConfig.getContextPath() + "/login.do?cmd=n6")%>" method="get"></go>
	进入游戏
	</anchor>
	<br />
	<anchor>
	<go href="<%=response.encodeURL(GameConfig.getContextPath() + "/login.do?cmd=n3")%>" method="get"></go>
	返回上一级
	</anchor>
</p>
</card>
</wml>
