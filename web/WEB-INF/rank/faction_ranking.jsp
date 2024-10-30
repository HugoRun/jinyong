<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@ include file="inc/faction_menu.jsp"%>
<c:if test="${empty faction_list}">
没有排行<br/>
</c:if>
<c:forEach items="${faction_list}" var="item" varStatus="status">
<c:if test="${status.index==0 }">
${first_des}
</c:if>
<c:if test="${status.index>0 }">
${status.index+1}.
</c:if>
<anchor>${ item.name}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="des" />
<postfield name="fId" value="${ item.id}" />
<postfield name="pre" value="${field }" />
</go>
</anchor>
声望:${ item.prestige}
<anchor>申请加入
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do")%>"> 
<postfield name="cmd" value="n9" />
<postfield name="fId" value="${ item.id}" />
<postfield name="field" value="${field }" />
</go>
</anchor>
<br/>
</c:forEach>
<%@ include file="/init/templete/game_foot.jsp"%>