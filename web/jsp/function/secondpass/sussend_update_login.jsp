<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		//String secondPass = (String)request.getParameter("pass_word");
		String pass_word = (String)request.getAttribute("pass_word");
	if(	pass_word != null || !pass_word.equals("")) { 
	 %>
	您成功设置了新的游戏帐号登录密码！修改后的密码是<%=pass_word %>！请您牢记该号码并不向外人透露！
<%} else { %>
	空指针！
<%} %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
