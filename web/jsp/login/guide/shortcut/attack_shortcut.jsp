<%@ include file="/init/templete/game_head.jsp" %>
<%@ page pageEncoding="UTF-8"%>
引导信息：请点击“攻击”，将基础攻击设置为快捷键，方便战斗！<br/>
请选择该快捷键设置的种类: <br/>
技能<br/>
药品<br/>
道具<br/>
查看<br/>
<anchor>攻击<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/guide.do?step=fight_page_2")%>" method="get"/></anchor><br/>
逃跑<br/>
返回<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>