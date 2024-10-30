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
		String hint = (String) request.getAttribute("hint");
		String type = (String) request.getAttribute("type");
	%>
	<%=hint %>
	<%if(type.equals("1")){ %>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/avoidpkprop.do?cmd=n2")%>"></go>确定</anchor>
	<%} %>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
