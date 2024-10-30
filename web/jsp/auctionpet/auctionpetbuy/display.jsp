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
	String resultWml = (String)request.getAttribute("resultWml");
	String pet_name = (String)request.getAttribute("pet_name");
	if( resultWml!=null )
	{
%> 
	<%=resultWml%>
<%
	}
	else
	{
		out.print("无");
	}
 %> 
 <br/>
 
 <% if ( pet_name == null || pet_name.equals("") || pet_name.equals("null")) { %>
 <anchor> <go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n1") %>"></go>返回</anchor>
  
 <%} else { %>
<anchor> <go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n4&amp;pet_name="+pet_name) %>"></go>返回</anchor>

<%} %>
<%@ include file="/init/init_time.jsp"%> 
</p>
</card>
</wml>
