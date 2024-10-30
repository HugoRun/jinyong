<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%String name = (String)request.getAttribute("name"); %>
红娘：原来你是瞧上了<%=name %>那丫头啊，呵呵～我这就去给你说道说道～有我出马保证你抱得美人归～你就准备好彩礼装备迎接新娘子吧！（请返回游戏主界面再刷新页面以等待对方玩家反应）<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
