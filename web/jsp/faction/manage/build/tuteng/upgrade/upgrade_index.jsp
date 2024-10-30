<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../../../inc/faction_head.jsp"%>
${faction.display }
--------------------<br/>
图腾列表:<br/>
<c:forEach items="${item_page.result}" var="item">
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do")%>"> 
<postfield name="cmd" value="ttDes" />
<postfield name="bId" value="${item.BId }" />
<postfield name="pre" value="upgradeIndex" />
</go>
${item.name }
</anchor>
${item.simpleDes }
<c:if test="${item.isUpgraded==true }">
<anchor>升级
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/fBuild.do")%>">
<postfield name="cmd" value="upgrade" />
<postfield name="bId" value="${item.BId }" />
</go>
</anchor>
</c:if>
<br/>
</c:forEach>
${item_page.pageFoot }
<%@ include file="../../../../inc/return_build_manage.jsp"%>