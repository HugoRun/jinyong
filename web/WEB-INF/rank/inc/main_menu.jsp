<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.rank.model.RankConstants"%>
【排行榜】<br/> 
<anchor>战力<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.KILL %>" /></go></anchor>
|<anchor>装备<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.SHENBING%>" /></go></anchor>
|生活
|<anchor>氏族<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.F_PRESTIGE %>" /></go></anchor>
|<anchor>活动<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.ANS %>" /></go></anchor>
<br/>