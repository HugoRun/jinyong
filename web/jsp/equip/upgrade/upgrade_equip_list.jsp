<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.*"%>
<%@ page import="com.ls.model.equip.EquipProduct" %>
【装备${equipProduct.actionName}】<br/>
请选择您要${equipProduct.actionName}的装备:<br/>
<% 
		EquipProduct equipProduct = (EquipProduct)request.getAttribute("equipProduct");
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
	<postfield name="pre" value="product" />
	</go>
	<%=equip.getFullName() %>
	</anchor>

	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
	<postfield name="cmd" value="n4" />
	<postfield name="pwPk" value="<%=equip.getPwPk() %>" />
	</go>
	 使用
	</anchor> 
	<br/>
	<%
	 		}}
	 		
	 	}
%>
<%=equip_page.getPageFoot() %><br/>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=productIndex")%>" method="get" /></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
