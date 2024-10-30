<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.*"%>
【装备镶嵌】<br/>
请选择您要镶嵌的装备:<br/>
<% 
	 	QueryPage equip_page = (QueryPage)request.getAttribute("equip_page");
		List equip_list = (List)equip_page.getResult();
		PlayerEquipVO equip = null;
	 	if( equip_list!=null && equip_list.size()!= 0)
	 	{
	 		for( int i=0;i<equip_list.size();i++)
	 		{
	 			equip = (PlayerEquipVO)equip_list.get(i);
	 			if( equip!=null ){
%>
	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
	<postfield name="cmd" value="des" />
	<postfield name="pwPk" value="<%=equip.getPwPk() %>" />
	<postfield name="page_no" value="<%=equip_page.getCurrentPageNo()%>" />
	<postfield name="pre" value="inlay" />
	</go>
	<%=equip.getFullName() %>
	</anchor>

	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
	<postfield name="cmd" value="inlayStoneList" />
	<postfield name="pwPk" value="<%=equip.getPwPk() %>" />
	</go>
	 使用
	</anchor> 
	<br/>
	<%
	 		}}
	 		
	 	}
%>
<%=equip_page.getPageFoot() %>
<%@ include file="/init/templete/game_foot.jsp"%>
