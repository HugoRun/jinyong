<%@page contentType="text/vnd.wap.wml" isELIgnored ="false" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerPropGroupVO"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="java.util.List"%>
<%@page import="com.ls.pub.constant.PropType"%>
<%@page import="com.ls.ben.vo.storage.*"%>
<% 
	 	QueryPage prop_page = (QueryPage)request.getAttribute("item_page");
		List prop_list = (List)prop_page.getResult();
		WareHouseVO wareHouseVO = null;
	 	if( prop_list!=null && prop_list.size()!= 0)
	 	{
	 		for( int i=0;i<prop_list.size();i++)
	 		{
	 			wareHouseVO = (WareHouseVO)prop_list.get(i);
	 			if(wareHouseVO.getUwPropNumber()>0){
%>
<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do")%>">
					<postfield name="cmd" value="n3" />
					<postfield name="w_type" value="${w_type }" />
					<postfield name="goods_id" value="<%=wareHouseVO.getUwPropId()%>" />
					<postfield name="warehouseID" value="<%=wareHouseVO.getUwId()%>" />
					<postfield name="page_no" value="${page_no }" />
					</go>
	 				<%= wareHouseVO.getUwArticle()%>×<%=wareHouseVO.getUwPropNumber() %>
	 				</anchor>|<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getStorage.do")%>">
					<postfield name="cmd" value="n2" />
					<postfield name="w_type" value="${w_type }" />
					<postfield name="prop_id" value="<%=wareHouseVO.getUwPropId()%>" />
					<postfield name="warehouseID" value="<%=wareHouseVO.getUwId()%>" />
					<postfield name="page_no" value="${page_no }" />
					</go>
					取出
					</anchor>
	 				<br/>
<%
	 		}}
	 		
	 	}
%>
<%=prop_page.getPageFoot()%>
