<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
<%
 	String hint = (String)request.getAttribute("hint");
	if(hint != null && !hint.equals("")){
	 %>
	 	<%=hint %>
	 <%}else { %>	
	对不起，您输入的二级密码组合不符合规定！游戏帐号的二级密码为6位0～9数值组合！
	<%} %>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/function/zhijieModify/reput_second_pass.jsp")%>">
	</go>
	返回
	</anchor>
<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/index.jsp")%>">
	</go>
	返回登录
	</anchor> 
	
</p>
</card>
</wml>
