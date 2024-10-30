<%@ include file="/init/templete/game_head.jsp" %>
<%@ page pageEncoding="UTF-8"%>
引导信息：请点击“快捷键”，进行药品的设置，进而更为方便的进行战斗！<br/>
攻击|<anchor>快捷键<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/guide.do?step=cure_shortcut")%>" method="get"/></anchor>|快捷键<br/>
您的气血:39564/39564<br/>
您的内力:7364/7364<br/>
----------------------<br/>
幽冥魔帝:35205/35205<br/>
快捷键|快捷键|快捷键<br/>
快捷键设置<br/>
自动战斗<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>