<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName}<br/>
您确定要解散氏族吗？<br/>
<anchor>确定<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=disband")%>" method="post"></go></anchor><br/>
<%@ include file="../../inc/return_faction_manage.jsp"%>