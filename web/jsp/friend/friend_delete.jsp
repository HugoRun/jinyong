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
	 String pByName =(String) request.getAttribute("pByName");
	 String pByPk = (String) request.getAttribute("pByPk"); 
	 String pageno = (String) request.getAttribute("page"); 
	 %>
	您确定要将玩家 <%=pByName %> 从好友列表中删除？
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="cmd" value="n7" />
	<postfield name="pByPk" value="<%=pByPk %>" />
	</go>
	确定
	</anchor>
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendaction.do") %>"> 
	<postfield name="cmd" value="n3" />
	<postfield name="pByPk" value="<%=pByPk %>" />
	<postfield name="pByName" value="<%=pByName %>" />
	</go>
	返回
	</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
