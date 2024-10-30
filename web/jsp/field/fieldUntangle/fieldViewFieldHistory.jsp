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
	int zhengpaiwin = (Integer)request.getAttribute("zhengpaiwin");
	int xiepaiwin = (Integer)request.getAttribute("xiepaiwin");
	int draw = (Integer)request.getAttribute("draw");
	String menuID = (String)request.getAttribute("menu_id");
%> 
	演武排行榜:<br/>
	正道<%=zhengpaiwin %>胜<br/>
	邪道<%=xiepaiwin %>胜<br/>
	平局<%=draw %>场<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fielduntangle.do?cmd=n2")%>">
	<postfield name="menu_id" value="<%=menuID %>" />
	</go>
 		查看本场排行榜
	</anchor><br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fielduntangle.do?cmd=n3")%>">
	<postfield name="menu_id" value="<%=menuID %>" />
	</go>
 		查看月排行榜
	</anchor>
<% %> 
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
