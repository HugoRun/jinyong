<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="ID" title="<s:message key = "gamename"/>">
<p>
<%-- 此页面也被战场管理员菜单所借用,如要修改此页面, 请通知张俊俊以便作出修改 --%>

	<% String display = request.getAttribute("display").toString();
	%>
<%=display%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
