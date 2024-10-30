<%@ page language="java" import="java.util.*,com.ls.ben.vo.info.npc.NpcVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员登陆页面</title>
		<link href="<%=GameConfig.getContextPath()%>/css/sys_css.css"
			rel="stylesheet" type="text/css" />
	</head>
	<script language="JavaScript">
	function npc(){
	if(/[^0-9]/g.test(taskform.npc_id.value))
	{
	alert("NPCID为数字");
	return false;
	}
	if(taskform.npc_id.value.length > 6){
	alert("超过长度了");
	return false;
	} 
	}
	</script>
	<body>
		<form name="taskform" action="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n13" method="post" onsubmit="return npc()">
			<table width="600" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="20" align="right" bgcolor="#66CCFF">
						输入NPCID：
					</td>
					<td height="20" bgcolor="#66CCFF">
						<input type="text" name="npc_id" />
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
						<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n14">全部重载NPC</a>
					</td>
				</tr>
			</table>
			<%
				NpcVO vo = (NpcVO) request.getAttribute("npcVO");
				if (vo != null) {
			%>
			<table  width="600"  border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="300" align="left" bgcolor="#66CCFF">
						npcidnpc_ID
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcID() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						名称npc_Name
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcName() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						气血npc_HP
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcHP() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						最大防御npc_defence_da
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getDefenceDa() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						最小防御npc_defence_xiao
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getDefenceXiao() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						金防御npc_jin_fy
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getJinFy() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						木防御npc_mu_fy
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMuFy() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						水防御npc_shui_fy
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getShuiFy() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						火防御npc_huo_fy
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getHuoFy() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						土防御npc_tu_fy
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTuFy() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						暴击率npc_drop
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getDrop() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						等级npc_Level
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getLevel() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						经验npc_EXP
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getExp() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						掉落钱数npc_money
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMoney() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						可否捕捉npc_take
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTake()  %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						刷新时间间隔npc_refurbish_time
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcRefurbishTime() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						NPC类型npc_type
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcType() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						NPC图片npc_pic
					</td>
					<td align="left" bgcolor="#66CCFF">
						 
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
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n15&npc_id=<%=vo.getNpcID() %>">确定重载该条菜单</a></td>
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
