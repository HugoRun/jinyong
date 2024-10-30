<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
${hint }
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=changeZZH")%>">
<postfield name="mId" value="${mId}"/>
</go>
</anchor><br/>
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=zlList")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>