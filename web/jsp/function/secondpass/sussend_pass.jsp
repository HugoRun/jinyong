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
		String secondPass = (String)request.getParameter("secondPass");
		String auth = (String)request.getAttribute("authenPass");
	if(	auth.equals("shizhen")) { 
	 %>
	您帐号的二级密码是<%=secondPass %>！请您牢记该号码并不向外人透露！
	
	<%-- ,这儿请别删除
	
	二级密码设置邮件已经被自动删除!
	<br/>
	您还可以修改登录密码:
	<input name="pass_word"  type="text" size="6"  maxlength="11" format="11M" />
	<br/>
	<anchor>
	<go method="post"   href="<%=GameConfig.getContextPath()%>/secondPass.do">
	<postfield name="cmd" value="n2" />
	<postfield name="pass_word" value="$pass_word" />
	</go>
	传送
	</anchor --%>
<%} else { %>
	空指针！
<%} %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
	