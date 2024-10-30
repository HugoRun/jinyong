<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
请选择要镶嵌的宝石:<br/>
<% 
	 	QueryPage prop_page = (QueryPage)request.getAttribute("stone_list");
		List prop_list = (List)prop_page.getResult();
		PlayerPropGroupVO stone = null;
	 	if( prop_list!=null && prop_list.size()!= 0)
	 	{
	 		for( int i=0;i<prop_list.size();i++)
	 		{
	 			stone = (PlayerPropGroupVO)prop_list.get(i);
	 			if( stone!=null && stone.getPropNum()>0)
	 			{
%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/prop.do")%>"> 
	<postfield name="cmd" value="des" />
	<postfield name="propId" value="<%=stone.getPropId() %>" />
	<postfield name="pwPk" value="${pwPk}" />
	<postfield name="pre" value="inlayStone" />
	</go>
	<%=stone.getPropName() %>
	</anchor>

	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
	<postfield name="cmd" value="inlayHint" />
	<postfield name="pwPk" value="${pwPk}" />
	<postfield name="propId" value="<%=stone.getPropId() %>" />
	</go>
	 使用
	</anchor><br/>
	<%
	 		}
	 	}
	 }
	 else
	 {
	 	%>无<%
	 }
		if( prop_page.hasNextPage() )
		{
	 %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do?cmd=inlayStoneList")%>">
<postfield name="page_no" value="<%=prop_page.getCurrentPageNo()+1%>" />
</go>
下一页
</anchor>
<%
		}
		if( prop_page.hasPreviousPage() )
		{
	%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do?cmd=inlayStoneList")%>">
<postfield name="page_no" value="<%=prop_page.getCurrentPageNo()-1%>" />
</go>
上一页
</anchor>
<%
		}
		if ( prop_page.getCurrentPageNo() == 1 && prop_page.getTotalPageCount() > 2) {		
	 %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do?cmd=inlayStoneList")%>">
<postfield name="page_no" value="<%=prop_page.getTotalPageCount() %>" />
</go>
到末页
</anchor>
<%}
	 if ( prop_page.getCurrentPageNo() == prop_page.getTotalPageCount() && prop_page.getTotalPageCount() > 2 ) 
	 {	 %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do?cmd=inlayStoneList")%>">
<postfield name="page_no" value="<%=1%>" />
</go>
到首页
</anchor>
<%} %>
<br/>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=inlayEquipList")%>" method="get" /></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
