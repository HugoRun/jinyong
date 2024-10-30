<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1") %>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
