<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
请选择可以增加概率的宝石:<br/>
<% 
	 	List stone_list = (List)request.getAttribute("stone_list");
		PlayerPropGroupVO stone = null;
	 	if( stone_list!=null && stone_list.size()!= 0)
	 	{
	 		for( int i=0;i<stone_list.size();i++)
	 		{
	 			stone = (PlayerPropGroupVO)stone_list.get(i);
	 			if( stone!=null ){
%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/prop.do")%>"> 
	<postfield name="cmd" value="des" />
	<postfield name="propId" value="<%=stone.getPropId() %>" />
	<postfield name="pre" value="rateStone" />
	</go>
	<%=stone.getPropName() %>*<%=stone.getPropNum() %>
	</anchor>

	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
	<postfield name="cmd" value="inputStoneNum" />
	<postfield name="propId" value="<%=stone.getPropId() %>" />
	</go>
	 使用
	</anchor> 
	<br/>
	<%
	 		}}
	 		
	 	}
%><br/>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=productIndex")%>" method="get" /></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
