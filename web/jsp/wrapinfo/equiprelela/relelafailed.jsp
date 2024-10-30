<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	<% 
	String hint = (String)request.getAttribute("hint");
	String return_type = (String)request.getAttribute("return_type");
	if ( hint != null && !hint.equals("") && !hint.equals("null")) {
	%>
	<%=hint%><br/>
	<%}
	if ( return_type != null && return_type.equals("body")) {
	%>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n3")%>"></go></anchor> 		
	<% } else { %>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1&amp;w_type=3")%>"></go></anchor>
	<%} %>
<br/>
<anchor>我要充值<go href="/sky/bill.do?cmd=n0" method="get"></go></anchor><br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
