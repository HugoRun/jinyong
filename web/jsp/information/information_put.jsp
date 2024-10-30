<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
		String type = request.getAttribute("type").toString();
	%>
	请认真输入您的当乐号:
	<br/>
	<input type="text" name="searchsign" />

	<br/>
	<anchor>
	<go method="get"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/information/information_reput.jsp")%>">
	<postfield name="id" value="$searchsign" />
	<postfield name="type" value="<%=type%>" />
	</go>
	确定
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
