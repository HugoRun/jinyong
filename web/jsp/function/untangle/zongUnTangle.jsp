<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"%> 
<%@page import="java.util.*,com.pm.dao.untangle.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
 	response.setContentType("text/vnd.wap.wml");
 %>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<!-- <%@ include file="/init/intimatehint.jsp"%> <br/> --> 
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n1")%>">
	</go>
	江湖排名
	</anchor>
	<br/>	
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n2")%>">
	</go>
	财富排名
	</anchor>
	<br/>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/tongranking.do?cmd=n1")%>">
	</go> 
	血榜排名
	</anchor>
	<br/>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n4")%>">
	</go> 
	神兵榜
	</anchor>	
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n7")%>">
	</go> 
	杀人榜
	</anchor>	
	<br/>
	<anchor>
	<go method="post"
		href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/function/function.jsp")%>">
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
