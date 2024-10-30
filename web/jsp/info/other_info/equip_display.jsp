<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
${equip_display }
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
 <postfield name="cmd" value="n16" />
<postfield name="other_p_pk" value="${pPks }" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>