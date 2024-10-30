<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.web.service.checkpcrequest.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<img src="<%=GameConfig.getGameUrl()%>/image/logo.png"  alt=""/><br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/tomlogin.do">
<postfield name="cmd" value="n2" />
<postfield name="lid" value="<%=(String)request.getAttribute("lid") %>" />
</go>
快速登陆
</anchor><br/>
2009年WAP网游真正巨作 <br/>
《<s:message key = "gamename"/>》横空出世！ <br/>
永久免费，大送工资！<br/>
全新浩大的<s:message key = "gamename"/>新世界！ <br/>
多重出生，多重天赋！ <br/>
能与小龙女结伴？能！ <br/>
我的江湖，我做主！<br/>
</p>
</card>
</wml>
