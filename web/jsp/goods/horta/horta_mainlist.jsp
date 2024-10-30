<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<c:if test="${!empty hint }">
${hint }<br/>
</c:if>
 【领取奖励】<br/>
<c:forEach items="${main_list}" var="award">
<anchor>${award.horta_name }${award.display }
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/horta.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="main_type" value="${award.hortaType }" />
</go>		
</anchor>
<br/>
</c:forEach>
<anchor>活动奖励<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sysprize.do?cmd=n1")%>"></go></anchor><br/>
<%@ include file="/init/templete/game_foot.jsp"%>
