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
	     String hint = (String)request.getAttribute("hint");
	     String pByuPk = (String)request.getAttribute("pByuPk");
	     String pByPk = (String)request.getAttribute("pByPk");
	%>
	<%=hint %>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do") %>">
	<postfield name="cmd" value="n3" />
	<postfield name="w_type" value="3" />
	<postfield name="pByuPk" value="<%=pByuPk %>" />
	<postfield name="pByPk" value="<%=pByPk %>" />
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
