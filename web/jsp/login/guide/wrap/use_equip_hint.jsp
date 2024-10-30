<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
引导信息：请点击“返回”，进入游戏的主界面，尝试与npc进行战斗！<br/>
你已装备上了【鸿蒙战甲】(套)！<br/>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/guide.do?step=fight_guide")%>"/></anchor><br/>
<%@ include file="/init/templete/card_foot.jsp"%>