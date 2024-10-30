<%@ page language="java" import="java.util.*,com.ls.ben.vo.info.npc.NpcdropVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员登陆页面</title>
		<link href="<%=GameConfig.getContextPath()%>/css/sys_css.css"
			rel="stylesheet" type="text/css" />
	</head>
	<script language="JavaScript">
	function npc_drop(){
	if(/[^0-9]/g.test(taskform.npc_drop_id.value))
	{
	alert("NPC掉落ID为数字");
	return false;
	}
	if(taskform.npc_drop_id.value.length > 6){
	alert("超过长度了");
	return false;
	} 
	}
	</script>
	<body>
		<form name="taskform" action="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n16" method="post" onsubmit="return npc_drop()">
			<table width="600" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="20" align="right" bgcolor="#66CCFF">
						输入NPC掉落ID：
					</td>
					<td height="20" bgcolor="#66CCFF">
						<input type="text" name="npc_drop_id" />
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
						全部重载NPC掉落
					</td>
				</tr>
			</table>
			<%
				NpcdropVO vo = (NpcdropVO) request.getAttribute("npcdropVO");
				if (vo != null) {
			%>
			<table  width="600"  border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="300" align="left" bgcolor="#66CCFF">
						npc掉落表npcdrop_ID
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcdropID() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						怪物idnpc_ID
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcID() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						物品类型goods_type
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getGoodsType() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						物品idgoods_id
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getGoodsId() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						物品名称goods_name
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getGoodsName() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						掉落该物品的几率npcdrop_probability
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcdropProbability() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						大暴几率npcdrop_luck
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcdropLuck() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						增加附加属性几率npcdrop_attribute_probability
					</td>
					<td align="left" bgcolor="#66CCFF">
						
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						掉落数量存储形式npcdrop_num
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcDropNum() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务掉落表npcdrop_taskid
					</td>
					<td align="left" bgcolor="#66CCFF">
						
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						此物品是否重要npcdrop_importance
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getNpcDropImprot() %>
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
					确定重载该条NPC掉落信息
					</td>
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
