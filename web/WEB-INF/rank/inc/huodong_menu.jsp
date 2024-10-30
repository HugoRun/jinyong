<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.rank.model.RankConstants"%>
<%@ include file="main_menu.jsp"%>
【活动排行榜】三千大道皆可成圣!<br/>
<anchor>神算榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.ANS %>" /></go></anchor>|<anchor>开光榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.OPEN %>" /></go></anchor><br/>
${detail}<br/>