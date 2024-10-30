<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	String user_name = (String)request.getAttribute("user_name");
	String pwd = (String)request.getAttribute("pwd");
	String lid = (String)request.getAttribute("lid");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
您已经成功注册了<s:message key = "gamename"/>的帐号!游戏帐号为:<%=user_name %>,登录密码为:<%=pwd%>请牢记您的游戏帐号和密码!<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do")%>">
<postfield name="cmd" value="n1" />
<postfield name="name" value="<%=user_name %>" />
<postfield name="paw" value="<%=pwd %>" />
<postfield name="lid" value="<%=lid %>" />
</go>
确定
</anchor><br/>
<!-- 
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/loginout.do?cmd=n2")%>"></go>重新登陆</anchor><br/> 
-->
</p>
</card>
</wml>
