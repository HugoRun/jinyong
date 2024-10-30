<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
String message = (String)request.getAttribute("message");
if(message!=null&&!"".equals(message.trim())){
%>
<%=message %><br/>
 <%}%>
 
请输入您报名所要花费的声望数量<br/>
<input type="text" name="sheng" format="*N" />
<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/battle.do")%>"> 
	<postfield name="cmd" value="n3" />  
	<postfield name="sheng" value="$(sheng)" /> 
	</go>
	确定
	</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/battle.do?cmd=n1")%>" method="get"></go>返回</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
