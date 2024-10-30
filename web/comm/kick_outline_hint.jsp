<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="wait" title="<bean:message key="gamename"/>">
<p>
<%
    String login_platform_url = GameConfig.getUrlOfLoginPlatform();
%>
该账号在其它地方登陆!<br/>
<anchor><go href="<%=login_platform_url %>" method="get"></go>返回专区</anchor> 
</p>
</card>
</wml>
