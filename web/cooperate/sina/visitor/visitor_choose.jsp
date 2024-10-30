<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="fail" title="<s:message key = "gamename"/>">
<p>
<%
	String login_platform_url = GameConfig.getUrlOfLoginPlatform();
%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/vis.do?cmd=n2")%>" method="get"></go>游客登陆</anchor>|<anchor><go href="http://3g.sina.com.cn/game/s/login/login.php?backURL=http%3A%2F%2F3g.sina.com.cn%2Fgame%2Fs%2Fgames%2Ftiexue.php&amp;backTitle=%CC%FA%D1%AA%CD%C0%C1%FA" method="get"></go>登陆新浪帐号</anchor>
<br/><anchor><go href="<%=login_platform_url %>" method="get"></go>返回专区</anchor>
</p>
</card>
</wml>