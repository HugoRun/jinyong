<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %> 
<card id="login" title="<s:message key = "gamename"/>">
<p>
<%
	String display = (String)request.getAttribute("display");
%>
<%=display %>
	<br/>
	<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/goods.do") %>">
<postfield name="cmd" value="n6" />
</go>
是
</anchor>
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/goods.do?cmd=n3")%>"></go>否</anchor><br/>
友情提醒:如果您选择否,您将放弃了本次拾取.<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
