<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
String message = (String)request.getAttribute("message");
String message1 = (String)request.getAttribute("message1");
if(message1!=null&&!"".equals(message1.trim())){
%>
<%=message1 %><br/>
 <%}if(message!=null&&!"".equals(message.trim())){
  %>
  <%=message %><br/>
  <%} %>
 
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
