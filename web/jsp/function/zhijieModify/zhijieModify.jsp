<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.pub.ben.info.*" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
				
				
	%>
	请输入您的游戏帐号。（游戏帐号为5-11位,0-9,a-z组合）<br/>
	账号:<input name="uName" type="text" maxlength="11" />(5-11位,0-9,a-z组合)
	<br/>
	
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/zhiModifyPassword.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="name" value="$uName" />
	</go>
	确定
	</anchor>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/index.jsp")%>">
	</go>
	返回登录
	</anchor> 
	<br/>
	如果您第一次登陆，您输入
	的账号和密码就将是您的
	注册信息，请牢记。<br/>
	
</p>
</card>
</wml>
