<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<%
		String type = request.getParameter("type").toString();
		String id = request.getParameter("id").toString();
	%>
	您提交的当乐号是<%=id%>!
	<br/>
	请再次输入您的当乐号:
	<br/>
	<input type="text" name="searchsign" />

	<br/>
	<anchor>
	<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/information.do")%>">
	<postfield name="reid" value="$searchsign" />
	<postfield name="id" value="<%=id%>" />
	<postfield name="type" value="<%=type%>" />
	<postfield name="cmd" value="n5" />
	</go>
	确定
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
