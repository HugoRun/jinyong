<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	请输入您要增加好友的名称:
	<br/>
	<input name="pByName" type="text" />
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="cmd" value="n10" />
	<postfield name="pByName" value="$pByName" />
	</go>
	确定
	</anchor>  
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
