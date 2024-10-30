<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.pub.constant.PropType"%>
<% 
	    String w_typeaa = (String)request.getAttribute("w_type");
	 	QueryPage prop_page = (QueryPage)request.getAttribute("item_page");
		List prop_list = (List)prop_page.getResult();
		PlayerPropGroupVO propGroup = null;
	 	if( prop_list!=null && prop_list.size()!= 0)
	 	{
	 		for( int i=0;i<prop_list.size();i++)
	 		{
	 			propGroup = (PlayerPropGroupVO)prop_list.get(i);
	 			if(propGroup.getPropNum()>0){
%>
					<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do")%>">
					<postfield name="cmd" value="n5" />
					<postfield name="w_type" value="<%=w_typeaa%>" />
					<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
					<postfield name="goods_id" value="<%=propGroup.getPropId()%>" />
					<postfield name="pageNo" value="<%=prop_page.getCurrentPageNo() %>" />
					</go>
	 				<%= propGroup.getPropName()%>×<%=propGroup.getPropNum() %>
	 				</anchor>|<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do")%>">
					<postfield name="cmd" value="n2" />
					<postfield name="w_type" value="<%=w_typeaa%>" />
					<postfield name="prop_id" value="<%=propGroup.getPropId()%>" />
					<postfield name="pg_pk" value="<%=propGroup.getPgPk()%>" />
					<postfield name="pageNo" value="<%=prop_page.getCurrentPageNo() %>" />
					</go>
					储存
					</anchor> 
	 				<br/>
<%
	 		}}
	 		
	 	}
%>
<%=prop_page.getPageFoot()%>
