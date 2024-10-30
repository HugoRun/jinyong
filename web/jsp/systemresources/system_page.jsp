<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员登陆页面</title>
		<link href="<%=GameConfig.getContextPath()%>/css/sys_css.css"
			rel="stylesheet" type="text/css" />
	</head>
	<body>
		<br />
		<br />
		<br />
		<br />
		<br />
		<center>
			欢迎登陆
		</center>
		<%
			String type = (String)request.getAttribute("type");
		%>
		<%@ include file="/jsp/systemresources/navigation_bar.jsp"%>
		<%
			if(Integer.parseInt(type) == 1){
		%>
		<%@ include file="/jsp/systemresources/task_page.jsp"%>
		<%
			}
		%>
		<%
			if(Integer.parseInt(type) == 2){
		%>
		<%@ include file="/jsp/systemresources/menu_page.jsp"%>
		<%
			}
		%>
		<%
			if(Integer.parseInt(type) == 3){
		%>
		<%@ include file="/jsp/systemresources/scene_page.jsp"%>
		<%
			}
		%>
		<%
			if(Integer.parseInt(type) == 4){
		%>
		<%@ include file="/jsp/systemresources/prop_page.jsp"%>
		<%}%>
		<%if(Integer.parseInt(type) == 5){%>
		<%@ include file="/jsp/systemresources/npc_page.jsp"%>
		<%}%>
		<%if(Integer.parseInt(type) == 6){%>
		<%@ include file="/jsp/systemresources/npc_drop_page.jsp"%>
		<%}%>
	</body>
</html>
