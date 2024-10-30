<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		int pg_pk = Integer.parseInt(request.getAttribute("pg_pk").toString());
		String wupinlan = (String)request.getAttribute("wupinlan");
		String page_no = (String)request.getSession().getAttribute("page_no");
	%>
	该道具点击确定使用就开始计算时间
	<br/>
	使用时间到道具即没有效果
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n18" /> 
	<postfield name="pg_pk" value="<%=pg_pk%>" /> 
	<postfield name="wupinlan" value="<%=wupinlan%>" /> 
	</go>
	确定使用
	</anchor>
	<br/>
	<% if(Integer.parseInt(wupinlan) == 0) {
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>"> 
	<postfield name="cmd" value="n1" /> 
	<postfield name="w_type" value="6" /> 
	<postfield name="page_no" value="<%=page_no %>" /> 
	</go>
	返回
	</anchor>
	<%}else{%>
	<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n3")%>" ></go>返回</anchor>
	<%
	} 
	%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>