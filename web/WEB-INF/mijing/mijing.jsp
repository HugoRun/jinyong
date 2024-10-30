<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
String message = (String)request.getAttribute("message");
String WHERE = (String)request.getAttribute("where");
if(where!=null&&!"".equals(where.trim())){
%>
<%=message %><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/lost.do")%>" method="post">
<postfield name="cmd" value="n1" />
</go>
是
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>否</anchor><br/>
  <%} %>
 
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
