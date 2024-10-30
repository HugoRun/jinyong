<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
【装备镶嵌】<br/>
您确定要将宝石镶嵌在该装备上吗？<br/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=inlay")%>" method="post" >
<postfield name="pwPk" value="${pwPk }"/>
<postfield name="propId" value="${propId }"/>
<postfield name="page_no" value="${page_no }"/>
</go>
</anchor><br/>
<anchor>返回
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=inlayEquipList")%>" method="post" >
<postfield name="page_no" value="${page_no }"/>
<postfield name="pwPk" value="${pwPk }"/>
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
