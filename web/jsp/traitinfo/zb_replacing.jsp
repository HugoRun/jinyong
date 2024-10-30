<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.*"%>
${hint }
<% 
	    String position = (String)request.getAttribute("position");
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
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n12" />
	<postfield name="pwPk" value="<%=equip.getPwPk() %>" />
	<postfield name="position" value="<%=position %>" />
	</go>
	<%=equip.getWName() %>
	</anchor>
	----
	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n11" />
	<postfield name="pwPk" value="<%=equip.getPwPk() %>" />
	<postfield name="position" value="<%=position %>" />
	</go>
	 使用
	</anchor> 
	<br/>
	<%
	 		}}
	 		
	 	}
	 	else
	 	{
	 		%>无<br/><%
	 	}
		if( equip_page.hasNextPage() )
		{
%>
<br/>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n10")%>">
<postfield name="page_no" value="<%=equip_page.getCurrentPageNo()+1%>" />
</go>
下一页
</anchor>
<%
		}
		if( equip_page.hasPreviousPage() )
		{
	%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n10")%>">
<postfield name="page_no" value="<%=equip_page.getCurrentPageNo()-1%>" />
</go>
上一页
</anchor>
<%
		}
		if ( equip_page.getCurrentPageNo() == 1 && equip_page.getTotalPageCount() > 2) {		
	 %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n10")%>">
<postfield name="page_no" value="<%=equip_page.getTotalPageCount() %>" />
</go>
到末页
</anchor>
<%}
	 if ( equip_page.getCurrentPageNo() == equip_page.getTotalPageCount() && equip_page.getTotalPageCount() > 2 ) 
	 {	 %>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n10")%>">
<postfield name="page_no" value="<%=1%>" />
</go>
到首页
</anchor>
<%} %>
<anchor><go method="get"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n3")%>" ></go>返回</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
