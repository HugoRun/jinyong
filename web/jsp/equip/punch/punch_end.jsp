<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
【装备打孔】<br/>
${hint }<br/>
<anchor>返回
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=punchEquipList")%>" method="post" >
<postfield name="page_no" value="${page_no }"/>
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
