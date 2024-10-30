<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
申请信息列表:<br/>
<c:forEach items="${item_page.result}" var="item">
<anchor>${item.roleEntity.name }
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>"> 
<postfield name="cmd" value="n13" />
<postfield name="pPks" value="${item.PPk }" />
<postfield name="backtype" value="applyList" />
</go>
</anchor>
(${item.roleEntity.grade }级)的加入氏族申请
<c:if test="${apply!=null }">
<anchor>批准
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="agreeApply" />
<postfield name="aId" value="${ item.id}" />
</go>
</anchor>
<anchor>删除
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="delApply" />
<postfield name="aId" value="${ item.id}" />
</go>
</anchor>
</c:if>
<br/>
</c:forEach>
${item_page.pageFoot }
<%@ include file="../../inc/return_faction_manage.jsp"%>