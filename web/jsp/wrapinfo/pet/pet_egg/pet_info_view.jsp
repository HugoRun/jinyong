<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
	<%   
		String petPk = (String) request.getAttribute("petPk");
		String resultWml = (String) request.getAttribute("resultWml");
	%> 
	<%
		if( resultWml!=null )
		{
			%><%=resultWml%><%
		}
		else
		{
			out.print("宠物信息错误<br/>");
		}
	 %>
	
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/petchuckaction.do")%>"> 
	<postfield name="petPk" value="<%=petPk%>" />  
	</go>
	遗弃
	</anchor>
	|
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do")%>">
	<postfield name="cmd" value="n4" />   
	<postfield name="petPk" value="<%=petPk%>" />  
	</go>
	改名
	</anchor>
	<br/>
	<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/petinfoaction.do?cmd=n1")%>" ></go>返回</anchor>  
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
