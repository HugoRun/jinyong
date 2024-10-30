<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
${hint }
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=jobMList")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>