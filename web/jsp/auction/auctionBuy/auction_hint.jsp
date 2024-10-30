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
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/jsp/auction/auctionMainPage/auction_main_shi.jsp")%>" method="get"></go>
	返回
	</anchor>  
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
