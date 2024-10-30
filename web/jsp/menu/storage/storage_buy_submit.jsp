<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<% String display = request.getAttribute("display").toString();
	int  num = Integer.parseInt(request.getAttribute("num").toString());
	%>
<%=display%><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do?cmd=n7") %>">
<postfield name="num" value="<%=num %>"/>
</go>
确定
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
