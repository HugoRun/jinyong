<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
<c:forEach items="${item_page.result}" var="item">
${ item.content}...(${ item.timeDes})
<c:if test="${del!=null }">
<anchor>删除
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do")%>"> 
<postfield name="cmd" value="delNoticeHint" />
<postfield name="id" value="${ item.id}" />
</go>
</anchor>
</c:if>
<br/>
</c:forEach>
${item_page.pageFoot }
<c:if test="${add!=null }">
<anchor>发布公告<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=inputNotice")%>"/></anchor><br/>
</c:if>
<%@ include file="../../inc/return_faction.jsp"%>