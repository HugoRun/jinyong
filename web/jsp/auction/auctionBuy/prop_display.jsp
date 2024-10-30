<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.pm.vo.constant.*" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p> 
<%
	String auction_type =(String) request.getAttribute("auction_type");
	String auction_id =(String) request.getAttribute("auction_id");
	String page_no =(String) request.getAttribute("page_no");
	String prop_id =(String) request.getAttribute("prop_id");
	
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
	<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do?cmd=n7")%>">
		<postfield name="w_type" value="<%=auction_type %>" />
		<postfield name="prop_id" value="<%=prop_id %>" />
		<postfield name="auction_id" value="<%=auction_id %>" />
		<postfield name="page_no" value="<%=page_no %>" />
		</go>
		购买
		</anchor><br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do?cmd=n2")%>">
	<postfield name="page_no" value="<%=page_no %>" />
	<postfield name="sortType" value="time" /> 
	</go>
	返回
	</anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
