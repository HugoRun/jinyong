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
	String menu_id = (String)request.getParameter("menu_id");
%> 
<input name="exchange_num"  type="text" size="5"  maxlength="5" format="5N" /><br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/exchange.do?cmd=n4")%>">
<postfield name="address" value="0" />
<postfield name="menu_id" value="<%=menu_id %>" />
<postfield name="exchange_num" value="$exchange_num" />
</go>
批量兑换
</anchor><br/>
<br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>