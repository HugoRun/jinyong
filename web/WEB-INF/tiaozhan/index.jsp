<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
Object pg_pk = request.getAttribute("pg_pk"); 
String message = (String)request.getAttribute("message");
if(message!=null&&!"".equals(message.trim())){
%>
<%=message %><br/>
 <%} %>
请您输入您要挑战的对象<br/>
<input type="text" name="name" maxlength="10" size="10"/>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/tiaozhan.do") %>"> 
	<postfield name="cmd" value="n1" />
	<postfield name="name" value="$(name)" />
	<postfield name="pg_pk" value="<%=pg_pk %>" />
	</go>
	确认
	</anchor> 
	<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
