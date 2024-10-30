<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
引导信息：请点击“返回”，回到仙书栏，学习【毁天灭地】技能！<br/>
恭喜您获得了技能书：【毁天灭地】，以及鸿蒙套装，你可在“仙书栏”中学习技能，在“装备栏”中将鸿蒙套装穿着于身上！<br/>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/guide.do?step=use_skillbook")%>"/></anchor><br/>
<%@ include file="/init/templete/card_foot.jsp"%>