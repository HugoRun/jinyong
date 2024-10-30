<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
你确定要给装备解除么?<br/>
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
<postfield name="cmd" value="unbind" />
<postfield name="pwPk" value="${pwPk}" />
<postfield name="pg_pk" value="${pg_pk }"/>
</go>
</anchor><br/>
<anchor>返回
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=bindEquipList")%>" method="post" >
<postfield name="page_no" value="${page_no }"/>
<postfield name="pg_pk" value="${pg_pk }"/>
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
