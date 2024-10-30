<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@ include file="inc/equip_menu.jsp"%>
<c:if test="${empty equip_list}">
没有排行
</c:if>
<c:choose>
<c:when test="${field=='e_zuoqi'}">
<c:forEach items="${equip_list}" var="equip"  varStatus="status">
<c:if test="${status.index==0 }">
${first_des}
</c:if>
<c:if test="${status.index>0 }">
${status.index+1}.
</c:if>
<anchor>${equip.mountInfo.name}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n7")%>">
<postfield name="pwPk" value="${equip.mountsID}" />
<postfield name="field" value="${field }" />
</go>
</anchor>
主人:
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n5")%>" method="post">
<postfield name="fd_pk" value="${equip.ppk}" />
<postfield name="field" value="${field }" />
</go>
${equip.partName}
</anchor>
<br/>
</c:forEach>
</c:when>

<c:otherwise>
<c:forEach items="${equip_list}" var="equip"  varStatus="status">
<c:if test="${status.index==0 }">
${first_des}
</c:if>
<c:if test="${status.index>0 }">
${status.index+1}.
</c:if>
<anchor>${equip.fullName}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n7")%>">
<postfield name="pwPk" value="${equip.pwPk}" />
<postfield name="field" value="${field }" />
</go>
</anchor>
主人:
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n5")%>" method="post">
<postfield name="fd_pk" value="${equip.PPk}" />
<postfield name="field" value="${field }" />
</go>
${equip.roleName}
</anchor>
<br/>
</c:forEach>
</c:otherwise>
</c:choose>
<%@ include file="/init/templete/game_foot.jsp"%>
