<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
请将结婚所需物品放在正确的位置上：<br/>
<%List<PlayerPropGroupVO> list = (List<PlayerPropGroupVO>)request.getAttribute("list"); 
String option = (String)request.getAttribute("option");
%>
<%for(PlayerPropGroupVO ppv : list){ %>
<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do")%>">
<postfield name="cmd" value="n3" />
     <postfield name="option" value="<%=option %>" />
	 <postfield name="propgroup" value="<%=ppv.getPgPk() %>" />
	 <postfield name="good_id" value="<%=ppv.getPropId() %>" />
	 <postfield name="caozuo" value="1" />
	 </go>
<%=ppv.getPropName() %></anchor><br/>
<%} %>
<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiehun.do")%>">
<postfield name="cmd" value="n5" />
	 </go>
返回</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
