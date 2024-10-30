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
		
		String auth = (String)request.getAttribute("authenPass");
		
		
	if(	auth.equals("erjipasswordistrue")) {
	 %>
	请输入该帐号新的登录密码:(帐号的登录密码为5-11位,0-9,a-z组合)
	<input name="pass_word"  type="text" size="6"  maxlength="11" format="11M" />
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/zhiModifyPassword.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="pass_word" value="$pass_word" />
	</go>
	传送
	</anchor>
<%} else { %>
	空指针！
<%} %>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/index.jsp")%>">
	</go>
	返回登录
	</anchor> 
</p>
</card>
</wml>
