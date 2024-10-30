<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
String message = (String)request.getAttribute("message");
if(message!=null&&!"".equals(message.trim())){
%>
<%=message %><br/>
 <%}%>
<%String baoming = (String)request.getAttribute("baoming"); 
  if(baoming==null||"".equals(baoming.trim())){
%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/battle.do?cmd=n2")%>" method="get"></go>比武报名</anchor><br/>
<%}else{ %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/battle.do?cmd=n4")%>" method="get"></go>进入擂台</anchor><br/>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/battle.do?cmd=n5")%>" method="get"></go>对阵查询</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
