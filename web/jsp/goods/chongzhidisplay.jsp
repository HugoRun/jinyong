<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="bill" title="兑换成功">
<p>
<%@ include file="/init/system/error_hint.jsp"%>
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/goods.do?cmd=n2")%>"></go>返回</anchor><br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>