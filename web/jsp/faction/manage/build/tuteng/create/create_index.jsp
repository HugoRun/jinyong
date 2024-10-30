<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../../../inc/faction_head.jsp"%>
${faction.display }
--------------------<br/>
图腾列表:<br/>
<c:forEach items="${item_page.result}" var="item">
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do")%>"> 
<postfield name="cmd" value="ttDes" />
<postfield name="bId" value="${item.id }" />
<postfield name="pre" value="createIndex" />
</go>
${item.name }
</anchor>
${item.simpleDes }
<anchor>建设
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do")%>">
<postfield name="cmd" value="create" />
<postfield name="bId" value="${item.id }" />
</go>
</anchor>
<br/>
</c:forEach>
${item_page.pageFoot }
<%@ include file="../../../../inc/return_tuteng_manage.jsp"%>