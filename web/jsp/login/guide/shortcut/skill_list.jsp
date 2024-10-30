<%@ include file="/init/templete/game_head.jsp" %>
<%@ page pageEncoding="UTF-8"%>
引导信息：请点击“毁天灭地”，将该技能设置为快捷键，方便在战斗中使用！<br/>
技能列表：<br/>
<anchor>毁天灭地<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/guide.do?step=fight_page_4")%>" method="get"/></anchor><br/>

<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>