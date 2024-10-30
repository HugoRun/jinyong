<%@ page pageEncoding="UTF-8" isErrorPage="false"%>
<%@taglib uri="/WEB-INF/tlds/c.tld"  prefix="c" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<anchor>更多游戏<go href="http://wapgame.189.cn" method="get"/></anchor><br/>
<anchor>点数专区<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/cooperate/tele/index/pointPage.jsp")%>" method="get"></go></anchor><br/>
<anchor>游戏帮助<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/cooperate/tele/index/gamehelp.jsp")%>"/></anchor><br/>
<anchor>关于<%=GameConfig.getGameName()%><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/cooperate/tele/index/abouteGame.jsp")%>" method="get"/></anchor><br/>