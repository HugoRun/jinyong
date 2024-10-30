<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.ben.rank.model.RankConstants"%>
<c:if test="${hint!=null }">
${hint}<br/>
${mountsHint}<br/>
</c:if>
<c:if test="${mountsHint!=null }">
${mountsHint}<br/>
</c:if>
<%@ include file="../faction/inc/faction_recruit_info.jsp"%>
<%@ include file="/init/systemInfo.jsp"%>
<c:if test="${tianguanhint!=null }">
${tianguanhint}
</c:if>
<anchor>刷新<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubaction.do")%>" method="get"></go></anchor>
${scene.fullName }<br/>
${scene.sceneDisplay }<br/>
${scene.bossRefHint }
<%@ include file="/init/init_menu.jsp"%>
<%@ include file="/init/main/npc_list.jsp"%>
${scene_walk_info }
<%@ include file="/init/intimatehintq.jsp"%>
<%@ include file="/init/main/player_list.jsp"%>
<%@ include file="/init/comm_index.jsp"%>
<%@ include file="/init/comm.jsp"%>
<%@ include file="/init/init_pkacteves.jsp"%>
<%@ include file="inc/down_menu.jsp"%>
----------------------<br/>
<%@ include file="/init/return_juu.jsp"%>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
