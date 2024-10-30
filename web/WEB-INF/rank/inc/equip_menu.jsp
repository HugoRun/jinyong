<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.rank.model.RankConstants"%>
<%@ include file="main_menu.jsp"%>
【装备排行榜】神兵仙甲,灵宝仙骑尽在此中!<br/>
<anchor>神兵榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.SHENBING %>" /></go></anchor>|<anchor>仙甲榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.XIANJIA %>" /></go></anchor>|<anchor>法宝榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.FABAO %>" /></go></anchor>|<anchor>坐骑榜<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.ZUOQI %>" /></go></anchor><br/>
${detail}<br/>