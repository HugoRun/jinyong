<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
引导信息：请点击“返回”，进入装备栏穿着装备！<br/>
你学会了毁天灭地技能！<br/>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/guide.do?step=use_equip")%>"/></anchor><br/>
<%@ include file="/init/templete/card_foot.jsp"%>