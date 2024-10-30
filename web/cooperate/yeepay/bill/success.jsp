<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<%
	String resultWml = (String)request.getAttribute("resultWml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值完成">
<p>
<%= resultWml%>
<anchor>
<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n0")%>"></go>返回商城</anchor><br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>