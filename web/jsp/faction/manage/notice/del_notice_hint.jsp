<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
您确定删除此条公告吗?<br/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=delNotice")%>" method="post">
<postfield name="id" value="${id}"/>
</go>
</anchor><br/>
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=noticeList")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>