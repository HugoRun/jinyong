<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
【镶嵌宝石】<br/>
${hint }<br/>
<anchor>返回
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=inlayEquipList")%>" method="post" >
<postfield name="page_no" value="${page_no }"/>
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
