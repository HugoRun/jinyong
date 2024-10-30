<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pub.ben.info.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<%  
	  String hint = (String)request.getAttribute("hint");
    %>
	<%
	if(hint!=null){
	%>
<%=hint %><br/>
	<% 
	} 
	 %> 
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubaction.do")%>"></go>取消</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
