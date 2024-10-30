<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../inc/faction_head.jsp"%>
请输入你所要发布的公告内容:<br/>
<input type="text" name="content" size="60" maxlength="60"/><br/>
<anchor>确定
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/faction.do?cmd=publishNotice")%>" method="post">
<postfield name="content" value="$(content)"/>
</go>
</anchor><br/>
<anchor>返回<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/faction.do?cmd=noticeList")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>