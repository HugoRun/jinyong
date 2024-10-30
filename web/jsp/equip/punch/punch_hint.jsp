<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
【装备打孔】<br/>
你确定要为此装备开孔吗？<br/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=punch")%>" method="post" >
<postfield name="pwPk" value="${pwPk }"/>
<postfield name="page_no" value="${page_no }"/>
</go>
</anchor><br/>
<anchor>返回
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=punchEquipList")%>" method="post" >
<postfield name="page_no" value="${page_no }"/>
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
