<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.lw.vo.systemnotify.SystemNotifyVO" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
<%
		SystemNotifyVO first_notify_info = (SystemNotifyVO)request.getAttribute("first_notify_info");
%> 

<%
	if( first_notify_info!=null )
	{ 
%>
<%=first_notify_info.getNotifytitle()%><br/>
<%=first_notify_info.getNotifycontent()%><br/>
<%
	}
%>
<%@ include file="/init/return_url/return_zhuanqu.jsp"%>
</p>
</card>
</wml>
