<%@ page pageEncoding="UTF-8"%>
<%@ include file="inc/faction_head.jsp"%>
<%@ include file="inc/faction_list.jsp"%>
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=des")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>