<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	 <%
	 String display = (String)request.getAttribute("display");
	 String goumai = (String)request.getAttribute("goumai");
	 String sk_id = (String)request.getAttribute("sk_id");
	 %>
	 <%if(display != null&& display !=""){
	 %>
	 <%=display %>
	 <%} %>
	 <br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/juexue.do")%>">
	<postfield name="cmd" value="n2" /> 
	<postfield name="sk_id" value="<%=sk_id %>" /> 
	</go>
	<%=goumai %>
	</anchor>
	<br/>
	<anchor>
	<go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n5")%>"> 
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
