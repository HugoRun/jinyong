<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.vo.tong.ranking.RankingVO"%>
    <% 
    RankingVO vo = (RankingVO)request.getAttribute("rankingVO");
    %>
    <%=vo.getTName() %><%if (Integer.parseInt(vo.getTCamp()) == 1) {%>(正)<%} else {%>(邪)<%}%>
	<br/>
	等级:<%=vo.getTongGrade() %>级帮
	<br/>
	帮主:<%=vo.getMemberName() %>
	<br/>
	现有帮众:<%=vo.getMemberNumber() %>人
	<br/>
	平均等级:<%=vo.getAverageGrade() %>级
	<br/>
	帮派驻地:无
	<br/>
	历次帮战杀人:<%=vo.getHomicideNumber() %>人次
	<br/>
<%@ include file="/WEB-INF/rank/beInclude.jsp"%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
