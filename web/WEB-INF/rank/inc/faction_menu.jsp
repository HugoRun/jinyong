<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.rank.model.RankConstants"%>
<%@ include file="main_menu.jsp"%>
【氏族排行榜】氏族实力的体现,问天下谁为最强氏族?<br/>
<anchor>战力榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.F_ZHANLI %>" /></go></anchor>
|战绩榜|攻城榜<br/>
<anchor>声望榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.F_PRESTIGE %>" /></go></anchor>
|
<anchor>财富榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.F_RICH %>" /></go></anchor>
<br/>
${detail}<br/>