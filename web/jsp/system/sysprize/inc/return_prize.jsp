<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<br/>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/horta.do?cmd=n1")%>" method="get"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>