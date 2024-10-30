<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
你确定要升级该装备的品质么?<br/>
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
<postfield name="cmd" value="upgradeQuality" />
<postfield name="pwPk" value="${pwPk}" />
<postfield name="pg_pk" value="${pg_pk }"/>
</go>
</anchor><br/>
<anchor>返回
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=qualityEquipList")%>" method="post" >
<postfield name="page_no" value="${page_no }"/>
<postfield name="pg_pk" value="${pg_pk }"/>
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
