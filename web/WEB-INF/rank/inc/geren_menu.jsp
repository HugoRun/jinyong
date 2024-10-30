<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.rank.model.RankConstants"%>
<%@ include file="main_menu.jsp"%>
【战力排行榜】个人实力的体现,问天下谁人无敌?<br/>
<anchor>战力榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.KILL %>" /></go></anchor>
|<anchor>等级榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.DENGJI %>" /></go></anchor>
|<anchor>血战榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.EVIL %>" /></go></anchor><br/>
<anchor>仙晶榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.YUANBAO %>" /></go></anchor>
|<anchor>仙石榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.MONEY %>" /></go></anchor>
|<anchor>死亡榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.DEAD %>" /></go></anchor><br/>
${detail}<br/>