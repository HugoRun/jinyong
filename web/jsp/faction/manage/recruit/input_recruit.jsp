<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
提示:发布招募信息需招募令×1,以及100灵石！<br/>
请输入您所要发布的招募信息:<br/>
<input type="text" name="rInfo" size="50" maxlength="50"/><br/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=recruit")%>" method="post">
<postfield name="rInfo" value="$(rInfo)"/>
</go>
</anchor><br/>
<%@ include file="../../inc/return_faction_manage.jsp"%>