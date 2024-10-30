<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.menu.OperateMenuVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p> 
<%
	String resultWml = (String)request.getAttribute("resultWml");
	String menu_id = (String)request.getAttribute("menu_id");
	if( resultWml!=null )
	{
%> 
	<%=resultWml%>
<%
	}
 %> 
 			<br/>
 			<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/buffMenu.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="menu_id" value="<%=menu_id %>" />
			<postfield name="reconfirm" value="reconfirm" />
			</go>
			领取
			</anchor>
			
 
<br/>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
 