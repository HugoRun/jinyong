<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>

	
	请输入您的二级密码(6位0～9数值组合):
	<br/>
	<input name="second_pass"  type="text" size="6"  maxlength="6" format="6N" />
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/zhiModifyPassword.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="second_pass" value="$second_pass" />
	</go>
	传送
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
