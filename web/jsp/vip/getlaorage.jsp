<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@page import="com.ls.pub.constant.Channel" %>
	<%if(GameConfig.getChannelId() == Channel.AIR){
		%>豪侠传奇会员每天可领取大量工资:<br/><%
	}else{
		%>铁血屠龙会员每天可领取大量工资:<br/><%
	}%>
${outprint }
<anchor>领取会员工资
<go method="post" href="<%=GameConfig.getContextPath()%>/vip.do">
<postfield name="cmd" value="n5" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>

