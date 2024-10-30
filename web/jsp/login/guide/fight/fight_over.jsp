<%@ include file="/init/templete/game_head.jsp" %>
<%@ page pageEncoding="UTF-8"%>
引导信息：恭喜您通过了基础训练，请点击“继续游戏”，正式体验洪荒世界的残酷与快意！<br/>
战斗胜利！<anchor>继续游戏<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/guide.do?step=accept_task")%>" method="get"/></anchor><br/>
幽冥魔帝:0/35205<br/>
您的体力:28734<br/>
您的内力:6347<br/>
您获得了:经验+0点<br/>
您获得了:灵石+15<br/>
全部拾取<br/>
踏雪无痕靴×1<br/>
<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>