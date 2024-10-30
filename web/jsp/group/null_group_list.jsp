<%@page pageEncoding="UTF-8"%>
<%@include file="/init/templete/game_head.jsp" %>
组队管理<br/>
您现在不在队伍中!<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n12")%>"></go>寻找队伍</anchor>
<%@include file="/init/templete/game_foot.jsp" %>
