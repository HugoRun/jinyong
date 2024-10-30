<%@ include file="/init/templete/game_head.jsp" %>
<%@page pageEncoding="UTF-8"%>
${other.des }
装备:
<c:forEach items="${other.equipOnBody.equipList}" var="equip">
${equip.WName }  
</c:forEach>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/pk.do")%>">
<postfield name="cmd" value="n11" />
<postfield name="aPpk" value="${me.PPk }" />
<postfield name="bPpk" value="${other.PPk }" />
</go>
返回
</anchor>
<br/>
<%@ include file="/init/templete/card_foot.jsp" %>
