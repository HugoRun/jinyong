<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
欢迎来到《<bean:message key="gamename"/>》的世界，一切从您开始……<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n2")%>"></go>进入游戏</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/loginout.do?cmd=n2")%>" method="get"></go>返回上级</anchor> 
</p>
</card>
</wml>
