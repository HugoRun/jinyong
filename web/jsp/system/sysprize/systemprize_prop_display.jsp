<%@page pageEncoding="UTF-8" %>
<%@ include file="/init/templete/game_head.jsp"%>
<c:if test="${!empty display }">
${display }<br/>
<anchor>返回
<go method="post"  href="<%=response.encodeURL(GameConfig.getContextPath()+"/sysprize.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="id" value="${id }" />
</go>
</anchor>
</c:if>
<%@ include file="/init/templete/game_foot.jsp"%>
