<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
你确定要修复该装备么?<br/>
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
<postfield name="cmd" value="maintainBad" />
<postfield name="pwPk" value="${pwPk}" />
<postfield name="pg_pk" value="${pg_pk }"/>
</go>
</anchor><br/>
<anchor>返回
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=badEquipList")%>" method="post" >
<postfield name="page_no" value="${page_no }"/>
<postfield name="pg_pk" value="${pg_pk }"/>
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>