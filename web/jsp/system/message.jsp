<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>   
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="message" title="<s:message key = "gamename"/>">
<p>
<%
	String hint = (String)request.getAttribute("result");
	if( hint!=null )
	{
	%><%=hint%><%
	}
	else
	{
	%>信息错误<%
	}
 %>
<%
	if(!hint.startsWith("交易需谨慎")&&!hint.endsWith("接受赠送"))
	{
		%>
<%@ include file="/init/init_time.jsp"%>
		<%
	}
 %>
</p>
</card>
</wml>
