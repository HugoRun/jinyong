<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
【功能列表】<br/>
<anchor>快捷设置<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do?cmd=n1&amp;shortType=1")%>" method="get"></go></anchor>
|
<anchor>图片设置<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=n1")%>" method="get"></go></anchor>
<br/>
<anchor>聊天设置<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=n4")%>" method="get"></go></anchor>
|
<anchor>交易设置<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=n6")%>" method="get"></go></anchor>
<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n6")%>"></go>副本信息</anchor>
|
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/blacklistaction.do?cmd=n3")%>"></go>黑名单</anchor>
<br/>
游戏论坛|<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/gmmail.do?cmd=n1")%>" ></go>联系GM</anchor>
<br/>
<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/loginout.do?cmd=n1")%>"></go>返回首页</anchor>

<%@ include file="/init/templete/game_foot.jsp"%>




