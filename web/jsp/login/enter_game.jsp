<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p> 
<%		
	String role_name = (String)request.getAttribute("role_name");
	String oneline_time = (String)request.getAttribute("oneline_time");
%> 
亲爱的<%=role_name%>,本周在线时间<%=oneline_time %>分钟!<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n6") %>" method="get"></go>进入游戏</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n3") %>" method="get"></go>返回上一级</anchor>
</p>
</card>
</wml>
