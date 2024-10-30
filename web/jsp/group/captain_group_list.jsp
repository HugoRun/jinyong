<%@page pageEncoding="UTF-8"%>
<%@include file="/init/templete/game_head.jsp" %>
组队管理<br/>
<c:choose>
<c:when test="${!empty group_info.memberList}">

<c:forEach items="${group_info.memberList}" var="member">
${member.name }(${member.basicInfo.grade}级)(${member.basicInfo.sceneInfo.sceneName})
<c:if test="${group_info.captianID==member.PPk}">
【队长】<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n7")%>"></go>解散</anchor>
</c:if>
<c:if  test="${me.PPk!=member.PPk}">
<anchor>移出
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n6")%>">
<postfield name="member_pk" value="${member.PPk }" />
</go>
</anchor>
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