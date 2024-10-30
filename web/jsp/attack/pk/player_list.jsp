<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<anchor>刷新<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do?cmd=n1")%>" method="get"></go></anchor><br/>
玩家列表<br/>
<c:forEach items="${item_page.result}" var="other">
<anchor>${other.basicInfo.displayName}
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
<postfield name="cmd" value="n13" />
<postfield name="pPks" value="${other.PPk}" />
</go>
</anchor>
${other.basicDes}
<%@ include file="inc/fight_action.jsp"%>
<c:if test="${me.isRookie==false}">
<anchor>组队
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do")%>">
<postfield name="cmd" value="n1" />
<postfield name="b_ppk" value="${other.PPk}" />
</go>
</anchor>
</c:if>
<c:if test="${me.isRookie==false}">
<anchor>交易
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
<postfield name="cmd" value="n1" />
<postfield name="pByPk" value="${other.PPk}" />
</go>
</anchor>
</c:if>
<br/>
</c:forEach>
${item_page.pageFoot }
<%@ include file="/init/templete/game_foot.jsp"%>