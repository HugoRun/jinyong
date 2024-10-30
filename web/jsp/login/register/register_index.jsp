<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<%
	String hint = (String) request.getAttribute("hint");
	String lid = (String) request.getAttribute("lid");
	if( lid==null || lid.isEmpty())//渠道为空时,设置默认值为99
	{
		lid = "99";
	}
	if (hint != null)
	{
	%><%=hint %><br/><%
	}
%>
请输入您要注册的游戏帐号和密码:<br/>
账号:<input name="uName" type="text" /><br/>
密码:<input name="uPaw" type="password" /><br/>
提示：游戏帐号为6-11位数字和大小写英文字符组合；密码为6位数字和大小写英文字符组合。<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n8")%>">
<postfield name="userName" value="$uName" />
<postfield name="paw" value="$uPaw" />
<postfield name="lid" value="<%=lid %>" />
</go>
注册
</anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/backdoor.jsp")%>"></go>返回上一级</anchor><br/>
</p>
</card>
</wml>
	