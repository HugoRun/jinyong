<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.ls.model.equip.EquipProduct" %>
<% 
		EquipProduct equipProduct = (EquipProduct)request.getAttribute("equipProduct");
%>
【装备${equipProduct.actionName}】<br/>
${hint }<br/>
<c:choose>
<c:when test="${empty equipProduct}">
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=productIndex")%>" method="post" />
</anchor>
</c:when>
<c:otherwise>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=n4")%>" method="post" />
<postfield name="pwPk" value="${pwPk}" />
</anchor>
</c:otherwise>
</c:choose>
<%@ include file="/init/templete/game_foot.jsp"%>
