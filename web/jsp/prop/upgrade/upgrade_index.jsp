<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.ls.model.equip.*" %>
<% 
	StoneProduct stoneProduct = (StoneProduct)request.getAttribute("stoneProduct");
%>
${hint }
【宝石合成】<br/>
<anchor>
${stoneProduct.stoneName}
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/prop.do?cmd=stoneList")%>" method="get" /></anchor>
…[${stoneProduct.curStoneNum}/${stoneProduct.stoneNum}]<br/>
升级石…[${stoneProduct.curMaterial1Num}/${stoneProduct.material1Num}]<br/>
成功率:${stoneProduct.successRate}%<br/>
升级费用:${stoneProduct.needMoneyDes}<br/>
去商城<br/>
<anchor>合成<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/prop.do?cmd=upgrade")%>" method="get" /></anchor><br/>
<%@ include file="/init/templete/game_foot.jsp"%>
