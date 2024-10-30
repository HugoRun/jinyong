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
	String w_type =(String) request.getAttribute("w_type");
	String resultWml = (String)request.getAttribute("resultWml");
	if( resultWml!=null )
	{
%> 
	<%=resultWml%>
<%
	}
	else
	{
		out.print("空指针");
	}%> 
	<br/>
	<%
	if(Integer.valueOf(w_type) ==3){ %> 
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n5")%>">
	<postfield name="table_type" value="<%=request.getSession().getAttribute("table_type") %>" />
	<postfield name="page_no" value="1" />
	</go>
	返回
	</anchor>
	
 <%}else { %>
 <anchor>
 <go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n1") %>">
 <postfield name="w_type" value="<%=w_type%>" />
 </go>
 返回
 </anchor>
 <%} %>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
