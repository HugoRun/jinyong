<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
${faction.fullName }<br/>
${hint }
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=changeTitle")%>">
<postfield name="mId" value="${mId}"/>
<postfield name="fTitle" value="${title}"/>
</go>
</anchor><br/>
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=titleMList")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>