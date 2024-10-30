<%@ include file="inc/pk_head.jsp" %>
<%@page pageEncoding="UTF-8"%>
<%@ include file="inc/shortcut.jsp" %>

您气血${playerA.PHp}/${playerA.PMaxHp}（-${playerA.playerInjure}
<c:if test="${!empty playerA.juexueInjure}">
${playerA.juexueInjure }
</c:if>
）<br/>
您内力${playerA.PMp}/${playerA.PMaxMp}
<c:if test="${playerA.expendMP>0}">
（-${playerA.expendMP}）
</c:if><br/>

<c:if test="${!empty playerA.contentdisplay}">
${playerA.contentdisplay}<br/>
</c:if>
<c:if test="${!empty playerA.skillDisplay}">
你使用了${playerA.skillDisplay}<br/>
</c:if>
<c:if test="${empty playerA.skillDisplay}">
${playerA.skillNoUseDisplay}<br/>
</c:if>
----------------------<br/>
${playerB.PName}气血${playerB.PHp}/${playerB.PMaxHp}（-${playerB.playerInjure}）<br/>
<c:if test="${!empty playerB.contentdisplay}">
${playerB.contentdisplay}<br/>
</c:if>

<c:if test="${!empty playerB.skillDisplay}">
${playerB.PName}使用了${playerB.skillDisplay}<br/>
</c:if>
	
	
<%@ include file="inc/fight_foot.jsp" %>
