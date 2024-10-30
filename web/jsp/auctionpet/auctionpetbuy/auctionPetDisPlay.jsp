<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.pm.vo.auctionpet.*"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*"%>
<%@page import="com.pm.vo.constant.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>	
	<%
	QueryPage name_page = (QueryPage)request.getAttribute("name_page");
	List name_list = (List)name_page.getResult();

	AuctionPetVO vo = null;
	%>
您搜索的物品:<br/>
名称|<anchor> 
		<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
		<postfield name="cmd" value="n4" />
		<postfield name="sortType" value="money" />
		<postfield name="pet_name" value="<%=request.getAttribute("pet_name") %>" />
		</go>
		价格
		</anchor><br/>
	 <%
	 	if( name_list!=null && name_list.size()!= 0)
	 	{
	 		for( int i=0;i<name_list.size();i++)
	 		{
	 			vo = (AuctionPetVO)name_list.get(i);
	 			if(i == name_list.size()-1 ){
	 			%> 
	 				<anchor>
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
					<postfield name="cmd" value="n5" /> 
					<postfield name="petPk" value="<%=vo.getPetPk() %>" /> 
					<postfield name="petId" value="<%=vo.getPetId() %>" /> 
					<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
					<postfield name="page_no" value="<%=name_page.getCurrentPageNo()%>" />
					<postfield name="pet_name" value="<%=request.getAttribute("pet_name") %>" />
					</go>
					<%= StringUtil.isoToGB(vo.getPetName())%>
					</anchor>|<%=MoneyUtil.changeCopperToStr(vo.getPetPrice()) %>|<anchor>
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
					<postfield name="cmd" value="n3" /> 
					<postfield name="petPk" value="<%=vo.getPetPk()%>" />
					<postfield name="pet_name" value="<%=request.getAttribute("pet_name") %>" />
					</go>
					拍买
					</anchor>
	 			<%
	 			}else{
	 			%> 
	 				<anchor>
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
					<postfield name="cmd" value="n5" /> 
					<postfield name="petPk" value="<%=vo.getPetPk() %>" /> 
					<postfield name="petId" value="<%=vo.getPetId() %>" /> 
					<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
					<postfield name="page_no" value="<%=name_page.getCurrentPageNo()%>" />
					<postfield name="pet_name" value="<%=request.getAttribute("pet_name") %>" />
					</go>
					<%= StringUtil.isoToGB(vo.getPetName())%>
					</anchor>|<%=MoneyUtil.changeCopperToStr(vo.getPetPrice()) %>|<anchor>
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
					<postfield name="cmd" value="n3" /> 
					<postfield name="petPk" value="<%=vo.getPetPk()%>" />
					</go>
					拍买
					</anchor>
	 				<br/>
	<%	}} 	
	} 	else 	{
	 		out.print("无");
	 	}
	  %> 
	   <br/>
	    <%
		if( name_page.hasNextPage() )
		{
	 %>
	
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n4")%>">
	<postfield name="page_no" value="<%=name_page.getCurrentPageNo()+1%>" />
	<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
	<postfield name="pet_name" value="<%=request.getAttribute("pet_name") %>" />
	</go>
	下一页
	</anchor>
	  
	<%
		}
		if( name_page.hasPreviousPage())
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n4")%>">
	<postfield name="page_no" value="<%=name_page.getCurrentPageNo()-1%>" />
	<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
	<postfield name="pet_name" value="<%=request.getAttribute("pet_name") %>" />
	</go>
	上一页
	</anchor>
	<%
		}
		if ( name_page.getCurrentPageNo() == 1 && name_page.getTotalPageCount() > 2) {	
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=name_page.getTotalPageCount()  %>" />
	<postfield name="searchType" value="<%=request.getAttribute("searchType") %>" />
	</go>
	到末页
	</anchor>
	 <%} 
	 if ( name_page.getCurrentPageNo() == name_page.getTotalPageCount() && name_page.getTotalPageCount() > 2 ) 
	 {	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=1  %>" />
	<postfield name="searchType" value="<%=request.getAttribute("searchType") %>" />
	</go>
	到首页
	</anchor>	 
	<%}  %>
	 <br/>

<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n1")%>"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>