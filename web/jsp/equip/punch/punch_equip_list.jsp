<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.*"%>
【装备打孔】<br/>
请选择您要打孔的装备:<br/>
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
	<postfield name="pre" value="punch" />
	</go>
	<%=equip.getFullName() %>
	</anchor>

	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
	<postfield name="cmd" value="punchHint" />
	<postfield name="pwPk" value="<%=equip.getPwPk() %>" />
	</go>
	 使用
	</anchor> 
	<br/>
	<%
	 		}}
	 		
	 	}
%><br/>
<%=equip_page.getPageFoot() %>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/wrap.do?cmd=n1")%>" method="get" /></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
