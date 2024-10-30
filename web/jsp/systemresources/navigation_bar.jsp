<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员登陆页面</title>
		<link href="<%=GameConfig.getContextPath()%>/css/sys_css.css"
			rel="stylesheet" type="text/css" />
	</head>
	<body>
		<table width="600" border="0" align="center" cellpadding="0" cellspacing="0">
			<tr>
				<td height="20" align="center" bgcolor="#66CCFF">
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n1&type=1">任务</a>
				</td>
				<td height="20" align="center" bgcolor="#66CCFF">
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n1&type=2">菜单</a>
				</td>
				<td height="20" align="center" bgcolor="#66CCFF">
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n1&type=3">scene地点</a>
				</td>
				<td height="20" align="center" bgcolor="#66CCFF">
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n1&type=4">道具</a>
				</td>
				<td height="20" align="center" bgcolor="#66CCFF">
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n1&type=5">NPC</a>
				</td>
				<td height="20" align="center" bgcolor="#66CCFF">
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n1&type=6">NPC掉落</a>
				</td>
			</tr>
		</table>
	</body>
</html>
