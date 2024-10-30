<%@page pageEncoding="UTF-8"%>
<%@include file="/init/templete/game_head.jsp" %>
组队管理<br/>
<c:choose>
<c:when test="${!empty group_info.memberList}">

<c:forEach items="${group_info.memberList}" var="member">
${member.name }(${member.basicInfo.grade}级)(${member.basicInfo.sceneInfo.sceneName})
<c:if test="${group_info.captianID==member.PPk}">
【队长】
</c:if>
<c:if test="${me.PPk==member.PPk}">
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n8")%>"></go>离开</anchor>
</c:if>
<br/>
</c:forEach>
${me.stateInfo.groupEffectDisplay}
</c:when>
<c:otherwise>
您目前没有组队
</c:otherwise>
</c:choose>
<%@include file="/init/templete/game_foot.jsp" %>