<%@ include file="inc/pk_head.jsp" %>
<%@ include file="inc/shortcut.jsp" %>
<%@page pageEncoding="UTF-8"%>

您气血${playerA.PHp }/${playerA.PMaxHp }
<c:if test="${!empty propUseEffect && propUseEffect.isEffected && propUseEffect.propType==1}">
(+${propUseEffect.effectValue })
</c:if>
<br/>
您内力${playerA.PMp }/${playerA.PMaxMp }
<c:if test="${!empty propUseEffect && propUseEffect.isEffected && propUseEffect.propType==2}">
(+${propUseEffect.effectValue })
</c:if>
<br/>
-------------------<br/>
${playerB.PName}气血${playerB.PHp }/${playerB.PMaxHp }<br/>


<c:if test="${!empty playerA.contentdisplay}">
${playerA.contentdisplay}<br/>
</c:if>

<c:if test="${!empty propUseEffect}">
<c:choose>
<c:when test="${propUseEffect.isEffected}">
${propUseEffect.effectDisplay }
</c:when>
<c:otherwise>
${propUseEffect.noUseDisplay }
</c:otherwise>
</c:choose>
<br/>
</c:if>

<c:if test="${!empty playerB.contentdisplay}">
${playerB.contentdisplay}<br/>
</c:if>
<%@ include file="inc/fight_foot.jsp" %>
