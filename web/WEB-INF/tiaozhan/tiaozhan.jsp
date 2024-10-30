<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
String message = (String)request.getAttribute("message");
Object tiao_ppk = request.getAttribute("tiao_ppk");
if(message!=null&&!"".equals(message.trim())){
%>
<%=message %>已经向你发起了挑战！是否接受？<br/>
<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/tiaozhan.do") %>"> 
	<postfield name="cmd" value="n2" />
	<postfield name="caozuo" value="caozuo" />
	<postfield name="tiao_ppk" value="<%=tiao_ppk %>" />
	</go>
	是
	</anchor> 
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/tiaozhan.do") %>"> 
	<postfield name="cmd" value="n2" />
	<postfield name="tiao_ppk" value="<%=tiao_ppk %>" />
	</go>
	否
	</anchor> 
	<br/>
 <%} %>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
