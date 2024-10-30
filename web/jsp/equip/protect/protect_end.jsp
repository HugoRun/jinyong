<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
${hint }
<anchor>继续绑定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=protectEquipList")%>" method="post" >
<postfield name="page_no" value="${page_no }"/>
<postfield name="pg_pk" value="${pg_pk }"/>
<postfield name="w_type" value="${w_type }"/>
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
