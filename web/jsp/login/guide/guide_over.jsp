<%@ include file="/init/templete/game_head.jsp" %>
<%@ page pageEncoding="UTF-8"%>
你通过基础训练,可以去探索世界了<br/>
<anchor>进入游戏<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/guide.do?step=accept_task")%>" method="get"/></anchor>
</p>
</card>
</wml>