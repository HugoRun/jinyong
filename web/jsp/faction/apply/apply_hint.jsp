<%@ page pageEncoding="UTF-8"%>
<%@ include file="../inc/faction_head.jsp"%>
${hint }
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=applyFList")%>" method="get" /></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>