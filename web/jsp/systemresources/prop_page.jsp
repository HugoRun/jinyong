<%@ page language="java" import="java.util.*,com.ls.ben.vo.goods.prop.PropVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员登陆页面</title>
		<link href="<%=GameConfig.getContextPath()%>/css/sys_css.css"
			rel="stylesheet" type="text/css" />
	</head>
	<script language="JavaScript">
	function prop(){
	if(/[^0-9]/g.test(taskform.prop_id.value))
	{
	alert("道具ID为数字");
	return false;
	}
	if(taskform.prop_id.value.length > 6){
	alert("超过长度了");
	return false;
	} 
	}
	</script>
	<body>
		<form name="taskform" action="<%=GameConfig.getContextPath()%>/systemresources.do?cmd=n10" method="post" onsubmit="return prop()">
			<table width="600" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="20" align="right" bgcolor="#66CCFF">
						输入道具ID：
					</td>
					<td height="20" bgcolor="#66CCFF">
						<input type="text" name="prop_id" />
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
						<a href="<%=GameConfig.getContextPath()%>/systemresources.do?cmd=n11">全部重载道具</a>
					</td>
				</tr>
			</table>
			<%
				PropVO vo = (PropVO) request.getAttribute("propVO");
					if (vo != null) {
			%>
			<table  width="600"  border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="300" align="left" bgcolor="#66CCFF">
						道具表prop_ID
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropID() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						名称prop_Name
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropName() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						类型prop_class
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropClass() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						使用等级范围prop_ReLevel
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropReLevel() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						绑定prop_bonding
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropBonding() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						重叠的数量prop_accumulate
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropAccumulate() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						说明prop_display
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropDisplay() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						卖出价prop_sell
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropSell() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						职业prop_job
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropJob() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						掉落率prop_drop
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropDrop() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						每天使用次数prop_usedegree
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropUsedegree() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						性别prop_sex
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropSex() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						保护prop_protect
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropProtect() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						图片prop_pic
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropPic() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						存储位置prop_position
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropPosition() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						拍卖场储存位置prop_auctionPosition
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropAuctionPosition() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						结婚要求prop_marriage
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getMarriage() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						特殊字节1prop_operate1
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropOperate1() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						特殊字节2prop_operate2
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropOperate2() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						特殊字节3prop_operate3
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropOperate3() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						是否需要二次确认prop_reconfirm
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropReconfirm() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						控制道具是否可用prop_use_control
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getPropUseControl() %>
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
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n12&prop_id=<%=vo.getPropID() %>">确定重载该条菜单</a></td>
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
