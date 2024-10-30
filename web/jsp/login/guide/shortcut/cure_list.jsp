<%@ include file="/init/templete/game_head.jsp" %>
<%@ page pageEncoding="UTF-8"%>
系统消息：请点击“黄中李”，将其设置为快捷键，更为方便在战斗中使用！<br/>
药品列表：<br/>
草还丹(极)<br/>
蟠桃(极)<br/>
<anchor>黄中李<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/guide.do?step=fight_page_3")%>" method="get"/></anchor><br/>

<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>