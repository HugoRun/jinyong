<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
请输入您所有更改的称号(5个字符之内):<br/>
<input type="text" name="fTitle" size="10" maxlength="10"/><br/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=changeTitleHint")%>" method="post">
<postfield name="fTitle" value="$(fTitle)"/>
<postfield name="mId" value="${mId }"/>
</go>
</anchor><br/>
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=titleMList")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>