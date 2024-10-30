<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>
    <%
    String pByPk = (String)request.getAttribute("pByPk");
    String pByName = (String)request.getAttribute("pByName"); 
    String backtype = (String)request.getSession().getAttribute("backtype");
     %>
玩家姓名:<%=pByName %><br/> 
消息内容:<br/>
<input name="upTitle" type="text" maxlength="20" /><br/>
	<anchor>
	<go method="post"  href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
	<postfield name="cmd" value="n8" /> 
	<postfield name="upTitle" value="$(upTitle)" /> 
	<postfield name="pByName" value="<%=pByName %>" />
	<postfield name="pByPk" value="<%=pByPk %>" />
	</go>
	确定
	</anchor>
	<br/>
	<%if (backtype != null&&backtype.contains("n")){%>
	 <anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n3")%>">
	 <postfield name="pPk" value="<%=pByPk%>" />
	 <postfield name="number" value="<%=backtype%>" />
	 </go>
	 返回
	 </anchor>
	 <%}else{%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>"> 
	<postfield name="cmd" value="n13" />   
	<postfield name="pPks" value="<%=pByPk %>" /> 
	</go>
	返回
	</anchor>
	 <%} %>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
