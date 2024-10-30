<%@ page language="java" import="java.util.*,com.ls.ben.vo.menu.OperateMenuVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员登陆页面</title>
		<link href="<%=GameConfig.getContextPath()%>/css/sys_css.css"
			rel="stylesheet" type="text/css" />
	</head>
	<script language="JavaScript">
	function menu(){
	if(/[^0-9]/g.test(taskform.menu_id.value))
	{
	alert("菜单ID为数字");
	return false;
	}
	if(taskform.menu_id.value.length > 6){
	alert("超过长度了");
	return false;
	} 
	}
	</script>
	<body>
		<form name="taskform" action="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n5" method="post" onsubmit="return menu()">
			<table width="600" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="20" align="right" bgcolor="#66CCFF">
						输入菜单ID：
					</td>
					<td height="20" bgcolor="#66CCFF">
						<input type="text" name="menu_id" />
					</td>
					<td height="20" bgcolor="#66CCFF">
						<input type="submit" name="Submit" value="提交" />
					</td>
				</tr>
			</table>
			</form>
			<table width="600" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="20" align="center" bgcolor="#66CCFF">
						<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n6">全部重载菜单</a>
					</td>
				</tr>
			</table>
			<%
				OperateMenuVO vo = (OperateMenuVO) request.getAttribute("operateMenuVO");
				if (vo != null) {
			%>
			<table  width="600"  border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="300" align="left" bgcolor="#66CCFF">
						id
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getId() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						菜单名称menu_name
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuName() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						菜单类型menu_type
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuType() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						每天使用次数menu_use_number
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuUseNumber() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						菜单图片menu_img
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuImg() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						菜单地点idmenu_map
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuMap() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						特殊字节1menu_operate1
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuOperate1() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						特殊字节2menu_operate2
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuOperate2() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						特殊字节3menu_operate3
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuOperate3() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						0为中立，1为正，2为邪menu_camp
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuCamp() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						对话menu_dialog
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuDialog() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						刷新时间1menu_time_begin
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuTimeBegin() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						刷新时间1menu_time_end
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuTimeEnd() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						刷新时间2menu_day_begin
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuDayBegin() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						刷新时间2menu_day_end
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuDayEnd() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						刷新时间menu_refurbish_time
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuRefurbishTime() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						父菜单idmenu_father_id
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuFatherId() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						排序显示menu_order
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuOrder() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务字符串menu_tasks_id
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuTasksId() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						是否是任务菜单0否，1是menu_task_flag
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMenuTaskFlag() %>
					</td>
				</tr>
				<tr>
					<td height="20" colspan="2" align="center" bgcolor="#66CCFF"></td>
				</tr>
				<tr>
					<td height="20" colspan="2" align="center" bgcolor="#66CCFF"></td>
				</tr>
				<tr>
					<td height="20" colspan="2" align="center" bgcolor="#66CCFF">
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n7&menu_id=<%=vo.getId() %>">确定重载该条菜单</a></td>
				</tr>
			</table>
			<br/>
			<br/>
			<br/>
			<br/>
			<br/>
			<%}else{%>
			<center>没有这条记录</center>
			<%}%>
	</body>
</html>
