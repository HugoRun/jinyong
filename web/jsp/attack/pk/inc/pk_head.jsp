<%@ include file="/init/templete/game_head.jsp" %>
<%@page pageEncoding="UTF-8"%>
<c:forEach items="${sys_info_list}" var="sys_info">
${sys_info.systemInfo }
</c:forEach>
<c:if test="${!empty attack_warning}">
${attack_warning }<br/>
</c:if>