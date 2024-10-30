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
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="type" value="1" />
	</go>
	学习技能
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="type" value="2" />
	</go>
	遗忘技能
	</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
