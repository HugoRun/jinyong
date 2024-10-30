<%@ page pageEncoding="UTF-8"%>
<%@ include file="../inc/faction_head.jsp"%>
<c:if test="${faction!=null }">
${faction.fullName}<br/>
</c:if>
${hint }
<%@ include file="../inc/return_faction_manage.jsp"%>