<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
【宝石合成】<br/>
请选择要合成的宝石:<br/>
<% 
	 	QueryPage prop_page = (QueryPage)request.getAttribute("stone_list");
		List prop_list = (List)prop_page.getResult();
		PlayerPropGroupVO stone = null;
	 	if( prop_list!=null && prop_list.size()!= 0)
	 	{
	 		for( int i=0;i<prop_list.size();i++)
	 		{
	 			stone = (PlayerPropGroupVO)prop_list.get(i);
	 			if( stone!=null&&stone.getPropNum()>0)
	 			{
%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/prop.do")%>"> 
	<postfield name="cmd" value="des" />
	<postfield name="propId" value="<%=stone.getPropId() %>" />
	<postfield name="pre" value="upgradeStone" />
	</go>
	<%=stone.getPropName() %>
	</anchor>

	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/prop.do")%>"> 
	<postfield name="cmd" value="inputStoneNum" />
	<postfield name="propId" value="<%=stone.getPropId() %>" />
	</go>
	 使用
	</anchor> 
	<br/>
	<%
	 			}
	 		}
	 	}
	 %>
<%=prop_page.getPageFoot() %>
<br/>
<anchor>返回<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/prop.do?cmd=upgradeIndex")%>" method="get" /></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
