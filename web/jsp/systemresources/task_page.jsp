<%@ page language="java" import="java.util.*,com.ben.vo.task.TaskVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员登陆页面</title>
		<link href="<%=GameConfig.getContextPath()%>/css/sys_css.css"
			rel="stylesheet" type="text/css" />
	</head>
	<script language="JavaScript">
	function task(){
	if(/[^0-9]/g.test(taskform.task_id.value))
	{
	alert("任务ID为数字");
	return false;
	}
	if(taskform.task_id.value.length > 6){
	alert("超过长度了");
	return false;
	} 
	}
	</script>
	<body>
		<form name="taskform" action="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n2" method="post" onsubmit="return task()">
			<table width="600" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="20" align="right" bgcolor="#66CCFF">
						输入任务ID：
					</td>
					<td height="20" bgcolor="#66CCFF">
						<input type="text" name="task_id" />
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
						<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n3">全部重载任务</a>
					</td>
				</tr>
			</table>
			<%
				TaskVO vo = (TaskVO) request.getAttribute("taskVO");
				if (vo != null) {
			%>
			<table  width="600"  border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="300" align="left" bgcolor="#66CCFF">
						任务标识t_id
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTId() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务组codet_zu
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTZu() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务组排序t_zuxl
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTZuxl() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务名t_name
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTName() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务最小等级t_level_xiao
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTLevelXiao() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务最大等级t_level_da
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTLevelDa() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务声望种类t_repute_type
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTReputeType() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务声望数值t_repute_value
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTReputeValue() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务声望性别t_sex
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTSex() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务领取门派t_school
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTSchool() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务类型t_type
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTType() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务时间t_time
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTTime() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						开始任务对白t_display
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTDisplay() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						开始任务提示t_tishi
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTTishi() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						开始任务提示的关键字，多个以,号分割t_key
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTKey() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						关键字所在的地图，多个以,号分割t_key_value
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTKeyValue() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务开始给予道具t_geidj
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTGeidj() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务开始给予道具数量t_geidj_number
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTGeidjNumber() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务开始给予装备t_geizb
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTGeizb() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务开始给予装备数量t_geizb_number
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTGeizbNumber() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						中间点t_point
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTPoint() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						通过中间点的描述t_zjms
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTZjms() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						不能通过中间点的描述t_bnzjms
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTBnzjms() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						通过中间点需要的物品t_zjdwp
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTZjdwp() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						通过中间点需要物品的数量t_zjdwp_number
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTZjdwpNumber() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						通过中间点需要的装备t_zjdzb
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTZjdzb() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						通过中间点需要装备的数量t_zjdzb_number
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTZjdzbNumber() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						通过中间点是否删除任务道具0不删除1删除t_djsc
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTDjsc() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						通过中间点是否删除装备0不删除1删除t_djsczb
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTDjsczb() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						通过中间点给的物品t_midst_gs
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTMidstGs() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						通过中间点给的装备t_midst_zb
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTMidstZb() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务需要道具t_goods
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTGoods() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务需要道具数量t_goods_number
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTGoodsNumber() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务需要装备t_goodszb
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTGoodszb() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务需要装备数量t_goodszb_number
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTGoodszbNumber() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务需要的杀戮t_killing
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTKilling() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务需要的杀戮数量t_killing_no
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTKillingNo() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务需要宠物t_pet
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTPet() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务需要宠物数量t_pet_number
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTPetNumber() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务金钱奖励t_money
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTMoney() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务经验奖励t_exp
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTExp() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务声望奖励种类t_sw_type
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTSwType() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						完成任务声望奖励t_sw
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTSw() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						物质奖励t_encouragement
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTEncouragement() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						物质奖励的数量t_encouragement_no
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTWncouragementNo() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						下一调记录开始npc的idt_xrwnpc_id
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTXrwnpcId() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						下一个记录的idt_next
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTNext() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						是否可放弃0可放弃1不可放弃t_abandon
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTAbandon() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						装备奖励t_encouragement_zb
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTEncouragementZb() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						装备奖励数量t_encouragement_no_zb
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTEncouragementNoZb() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						是否可循环接受任务 0 不可以循环 1可以循环t_cycle
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTCycle() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						任务放弃t_waive
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getTWaive() %>
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
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n4&task_id=<%=vo.getTId() %>">确定重载该条任务</a></td>
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
