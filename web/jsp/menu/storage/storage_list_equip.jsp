<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.QueryPage,java.util.List"%>
<% 
	    String w_typeaa = (String)request.getAttribute("w_type");
	 	QueryPage equip_page = (QueryPage)request.getAttribute("item_page");
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
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do")%>"> 
	<postfield name="cmd" value="n4" />
	<postfield name="pageNo" value="<%=equip_page.getCurrentPageNo() %>" />
	<postfield name="w_type" value="<%=w_typeaa %>" />
	<postfield name="pwPk" value="<%=equip.getPwPk() %>" />
	</go>
	<%=equip.getFullName() %>
	</anchor>|<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="w_type" value="<%=w_typeaa %>" />
	<postfield name="equip_id" value="<%=equip.getPwPk() %>" />
	<postfield name="pageNo" value="<%=equip_page.getCurrentPageNo() %>" />
	</go>
	储存
	</anchor><br/>
	<%
	 		}}
	 		
	 	}
%>
<%=equip_page.getPageFoot()%>

