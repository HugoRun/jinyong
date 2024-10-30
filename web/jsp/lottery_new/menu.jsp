<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%  
String display = (String)request.getAttribute("display");
%>
<%=display %>
<br/>	
<anchor>
<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do?cmd=n3")%>">
</go>
我要竞猜
</anchor>
|
<anchor>
<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do?cmd=n4")%>">
</go>
我要领奖
</anchor>
<br/>
<anchor>
<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do?cmd=n8")%>"></go>
历史号码
</anchor>
|
<anchor>
<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do?cmd=n6")%>"></go>
竞猜规则
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
