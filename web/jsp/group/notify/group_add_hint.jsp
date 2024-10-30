<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="ID" title="<bean:message key="gamename"/>">
<p>
<%
	String hint = request.getParameter("hint");
	if(hint == null || hint.equals("") || hint.equals("null")){
		hint = (String)request.getAttribute("hint");
	}
%>
<%=hint %>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
