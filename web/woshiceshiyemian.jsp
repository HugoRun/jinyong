<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<%
	String hint = (String) request.getAttribute("hint"); 
	if (hint != null)
	{
	%><%=hint %><br/><%
	}
%>
账号:<input name="uName" type="text"  maxlength="11" /><br/>
密码:<input name="uPaw" type="password" maxlength="11" />(5-11位,0-9,a-z组合)<br/>
<anchor>
<go method="post" href="<%=GameConfig.getContextPath()%>/login.do?cmd=n10">
<postfield name="name" value="$uName" />
<postfield name="paw" value="$uPaw" />
</go>
确定
</anchor><br/>
</p>
</card>
</wml>
