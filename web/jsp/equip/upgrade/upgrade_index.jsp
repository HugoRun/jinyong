<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.ls.model.equip.EquipProduct" %>
<% 
	EquipProduct equipProduct = (EquipProduct)request.getAttribute("equipProduct");
%>
${hint }
【装备${equipProduct.actionName}】<br/>
请选择您要${equipProduct.actionName}的装备:<br/>
<anchor>
${equipProduct.equipName}
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=n1")%>" method="get" /></anchor><br/>
所需材料:<br/>
${equipProduct.needMaterialsDes}
<anchor>
${equipProduct.rateStoneName}
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=stoneList")%>" method="get" /></anchor>
…[${equipProduct.curRateStoneNum}/${equipProduct.rateStoneNum}]<br/>
<%
	if( equipProduct.isCanUseProtectedStone())
	{
%>
<anchor>
${equipProduct.protectStoneName}
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=useProtStoneHint")%>" method="get" /></anchor>
…[${equipProduct.protectStoneNum}/1]<br/>
<%} %>
成功率:${equipProduct.successRate}%<br/>
升级费用:${equipProduct.needMoneyDes}<br/>
去商城<br/>
<anchor>${equipProduct.actionName}<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=n2")%>" method="get" /></anchor><br/>
<%@ include file="/init/templete/game_foot.jsp"%>
