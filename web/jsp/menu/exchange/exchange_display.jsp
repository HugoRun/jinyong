<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p> 
<%
	String resultWml = (String)request.getAttribute("resultWml");
	if( resultWml!=null )
	{
%> 
<%=resultWml%>
<%
	}
	else
	{
		out.print("系统发生错误，请稍等片刻后再试！");
	}
 %> 
 <br/>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
 