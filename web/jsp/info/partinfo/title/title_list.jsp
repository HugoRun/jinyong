<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@ include file="../part_info_menu_head.jsp"%>
<c:if test="${empty list}">
暂无称号<br/>
</c:if>
<c:forEach items="${list}" var="title">
<anchor>${title.name }(${title.typeName })
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/title.do")%>">
<postfield name="cmd" value="n2" />  
<postfield name="tId" value="${title.TId }" />  
</go>
</anchor>
|
<c:if test="${title.isShow==-1}">
<anchor>显示
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/title.do")%>">
<postfield name="cmd" value="n3" />  
<postfield name="tId" value="${title.TId }" />  
</go></anchor>
</c:if>
<c:if test="${title.isShow==1}">
<anchor>不显示
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/title.do")%>">
<postfield name="cmd" value="n3" />  
<postfield name="tId" value="${title.TId }" />  
</go></anchor>
</c:if>
${title.leftTimeDes }
<br/>
</c:forEach>
<%@ include file="../part_info_menu_foot.jsp"%>
<%@ include file="/init/templete/game_foot.jsp"%>
