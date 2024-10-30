<%@page pageEncoding="UTF-8" %>
<%@ include file="/init/templete/game_head.jsp"%>
<c:choose>
<c:when test="${empty list }">
您当前没有任何奖励可以领取!<br/>
</c:when>
<c:otherwise>
请尽快领取属于您的物品!<br/>
<c:forEach items="${list }" var="award">
<anchor>${award.prizetype }
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sysprize.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="id" value="${award.id }" />
</go>
</anchor><br/>
</c:forEach>
</c:otherwise>
</c:choose>
<%@ include file="inc/return_prize.jsp"%>
