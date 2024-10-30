<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
请选择使用保底宝石:<br/>
<% 
		String propId = (String)request.getAttribute("propId");
	 	if( propId!=null )
	 	{
%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/prop.do")%>"> 
	<postfield name="cmd" value="des" />
	<postfield name="propId" value="${propId }" />
	<postfield name="pre" value="proStone" />
	</go>
	${propName }
	</anchor>

	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
	<postfield name="cmd" value="useProtStone" />
	<postfield name="propId" value="${propId }" />
	</go>
	使用
	</anchor> 
	<br/>
	<%
	 }
	 else
	 {
%>无<%
	 }
%><br/>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=productIndex")%>" method="get" /></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
