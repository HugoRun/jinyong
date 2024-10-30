<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
您确定将${member.name }逐出氏族吗？<br/>
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=delMem")%>">
<postfield name="mId" value="${member.PPk }"/>
</go>
</anchor><br/>
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=memList")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>