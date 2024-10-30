<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>   
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="message" title="<bean:message key="gamename"/>">
<p>
<anchor>
<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/gmkick.do?cmd=n1")%>">
</go>
踢人
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
