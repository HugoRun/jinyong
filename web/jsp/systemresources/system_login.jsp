<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员登陆页面</title>
		<link href="<%=GameConfig.getContextPath()%>/css/sys_css.css" rel="stylesheet" type="text/css" />
	</head>
	<script language="JavaScript">
	function login(){
	var name = loginform.username.value;
	if(name == ""){
	alert("用户名不能为空");
	loginform.username.focus();
	return false;
	}
	var paw = loginform.userpaw.value;
	if(paw == ""){
	alert("密码不能为空");
	loginform.userpaw.focus();
	return false;
	}
	}
	</script>
	<body>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
	<br/>
		<form name="loginform" action="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n0" method="post" onsubmit="return login()">
		<%
		String hint = (String)request.getAttribute("hint");
		if(hint != null){%><center><font color="#FF0000"><%=hint%></font></center><%}%>
			<table width="200" height="73" align="center">
				<tr>
					<td>
						用户名:
					</td>
					<td>
						<input type="text" name="username" />
					</td>
				</tr>
				<tr>
					<td>
						密码:
					</td>
					<td>
						<input type="text" name="userpaw" />
					</td>
				</tr>
				<tr>
					<td height="25" colspan="2" align="center">
						<input type="submit" name="Submit" value="提交" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
