<%@ page language="java" import="java.util.*,com.ls.ben.vo.map.SceneVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>系统管理员登陆页面</title>
		<link href="<%=GameConfig.getContextPath()%>/css/sys_css.css"
			rel="stylesheet" type="text/css" />
	</head>
	<script language="JavaScript">
	function scene(){
	if(/[^0-9]/g.test(sceneform.scene_id.value))
	{
	alert("菜单ID为数字");
	return false;
	}
	if(sceneform.scene_id.value.length > 6){
	alert("超过长度了");
	return false;
	} 
	}
	</script>
	<body>
		<form name="sceneform" action="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n8" method="post" onsubmit="return scene()">
			<table width="600" border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td height="20" align="right" bgcolor="#66CCFF">
						输入scene地点ID：
					</td>
					<td height="20" bgcolor="#66CCFF">
						<input type="text" name="scene_id" />
					</td>
					<td height="20" bgcolor="#66CCFF">
						<input type="submit" name="Submit" value="提交" />
					</td>
				</tr>
			</table>
			</form>
			<%
				SceneVO vo = (SceneVO) request.getAttribute("sceneVO");
				if (vo != null) {
			%>
			<table  width="600"  border="0" align="center" cellpadding="0"
				cellspacing="0">
				<tr>
					<td width="300" align="left" bgcolor="#66CCFF">
						地点IDscene_ID
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneID() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						场景名称scene_Name
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneName() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						场景坐标scene_coordinate
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneCoordinate() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						传送限制scene_limit 
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneLimit() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						地图跳转scene_jumpterm
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneJumpterm() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						地图属性(可以是几个)scene_attribute
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneAttribute() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						PK开关,1表示安全scene_switch
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneSwitch() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						视野编号scene_ken
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneKen() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						场景技能scene_skill
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneSkill() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						场景图片scene_photo
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getScenePhoto() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						场景说明scene_display
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneDisplay() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						MPA区域scene_mapqy
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneMapqy() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						上行地点scene_shang
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneShang() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						下行地点scene_xia
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneXia() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						左行地点scene_zuo
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneZuo() %>
					</td>
				</tr>
				<tr>
					<td align="left" bgcolor="#66CCFF">
						右行地点scene_you
					</td>
					<td align="left" bgcolor="#66CCFF">
						<%=vo.getSceneYou() %>
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
					<a href="<%=GameConfig.getContextPath() %>/systemresources.do?cmd=n9&scene_id=<%=vo.getSceneID() %>">确定重载该条scene地点</a></td>
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
