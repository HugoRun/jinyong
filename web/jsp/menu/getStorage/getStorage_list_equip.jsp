<%@page contentType="text/vnd.wap.wml" isELIgnored ="false" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.QueryPage,java.util.List"%>
<% 
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
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="pwPk" value="<%=equip.getPwPk() %>" />
	<postfield name="page_no" value="${page_no }" />
	<postfield name="w_type" value="${w_type }" />
	</go>
	<%=equip.getFullName() %>
	</anchor>|<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="WPk" value="<%=equip.getPwPk() %>" />
	<postfield name="w_type" value="${w_type }" />
	<postfield name="page_no" value="${page_no }" />
	</go>
	取出
	</anchor>
	<br/>
	<%
	 		}}
	 		
	 	}
%>

<%=equip_page.getPageFoot()%>

