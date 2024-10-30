<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.pub.*,com.ben.vo.communion.CommunionVO,com.pub.ben.info.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<% 
		CommunionVO communionVO = (CommunionVO) request.getAttribute("communionVO");  
		if (communionVO != null) {  
	%>
	<%
		if (communionVO.getCType() == 1) {
	%>
	[公]
	<%
		} else if (communionVO.getCType() == 2) {
	%>
	[种]
	<%
		} else if (communionVO.getCType() == 3) {
	%>
	[队]
	<%
		} else if (communionVO.getCType() == 4) {
	%>
	[氏]
	<%
		} else if (communionVO.getCType() == 5) { 
	%>
	[私]
	<%
		} 
	%>
	<%=communionVO.getPName()%>:
	<br />
	<%=communionVO.getCTitle()%>
	<%
		} 
	%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
