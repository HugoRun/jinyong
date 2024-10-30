<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="ID" title="<bean:message key="gamename"/>">
<p>
<%
	String display = (String)request.getAttribute("display");
%>
寻找队伍<br/>
<%=display %>
<br/><anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n2")%>"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
