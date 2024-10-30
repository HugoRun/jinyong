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
		String firstPassword = (String)request.getAttribute("firstPassword");
		String auth = (String)request.getAttribute("authenPass");
		String hint = (String)request.getAttribute("hint");
	if(	auth.equals("reputpassword")) { 
		if(hint != null && !hint.equals("")) {
	 %>
	<%=hint %>
	<%} %>
	请再次输入您新的帐号登录密码:
	<input name="re_pass_word"  type="text" size="6"  maxlength="11" format="11M" />
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/modifyPassword.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="re_pass_word" value="$re_pass_word" />
	<postfield name="firstPassword" value="<%=firstPassword %>" />
	</go>
	传送
	</anchor>
<%} else { %>
	空指针！
<%} %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
