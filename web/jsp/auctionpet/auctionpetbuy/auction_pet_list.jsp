<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.util.*,com.pm.vo.auctionpet.AuctionPetVO"%>
<%@page import="com.ben.vo.petinfo.PetInfoVO"  %>
<%@page import="com.ls.pub.bean.*"%>
<%@page import="java.util.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>

<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
<%
	String menu_id = (String)request.getAttribute("menu_id");
	
	QueryPage pet_page = (QueryPage)request.getAttribute("pet_page");
	List auctionPetList = (List)pet_page.getResult();
%> 
拍卖场宠物列表:<br/>
			<anchor> 
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>"> 
			<postfield name="cmd" value="n1" /> 
			<postfield name="searchType" value="goods" /> 
			</go>
			名称
			</anchor>|<anchor> 
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>"> 
			<postfield name="cmd" value="n1" /> 
			<postfield name="searchType" value="money" /> 
			</go>
				价格
			</anchor><br/>
<%
	if( auctionPetList!=null && auctionPetList.size()!=0 )
	{
		for( int i=0;i<auctionPetList.size();i++ )
		{
			AuctionPetVO vo= (AuctionPetVO)auctionPetList.get(i);
			if(i == auctionPetList.size()-1){
			%>
			<anchor> 
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>"> 
			<postfield name="cmd" value="n2" /> 
			<postfield name="petPk" value="<%=vo.getPetPk() %>" /> 
			<postfield name="petId" value="<%=vo.getPetId() %>" /> 
			<postfield name="page_no" value="<%=pet_page.getCurrentPageNo()%>" />
			<postfield name="searchType" value="<%=request.getAttribute("searchType") %>" />
			</go>
			<%=StringUtil.isoToGB(vo.getPetName())%>
			</anchor>|<%=MoneyUtil.changeCopperToStr(vo.getPetPrice()) %>|<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
			<postfield name="cmd" value="n3" /> 
			<postfield name="petPk" value="<%=vo.getPetPk()%>" />
			</go>
			拍买
			</anchor><br/>
			<%
			}else{
			%>
			<anchor> 
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>"> 
			<postfield name="cmd" value="n2" /> 
			<postfield name="petPk" value="<%=vo.getPetPk() %>" /> 
			<postfield name="petId" value="<%=vo.getPetId() %>" /> 
			<postfield name="page_no" value="<%=pet_page.getCurrentPageNo()%>" />
			<postfield name="searchType" value="<%=request.getAttribute("searchType") %>" />
			</go>
			<%=StringUtil.isoToGB(vo.getPetName())%>
			</anchor>|<%=MoneyUtil.changeCopperToStr(vo.getPetPrice()) %>|<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
			<postfield name="cmd" value="n3" /> 
			<postfield name="petPk" value="<%=vo.getPetPk()%>" />
			</go>
			拍买
			</anchor><br/>
			<%
		}
		}
 	}
 	else
 	{
 		%>暂无宠物<% 
 	}
  %><br/>
  <%
		if( pet_page.hasNextPage() )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=pet_page.getCurrentPageNo()+1%>" />
	<postfield name="searchType" value="<%=request.getAttribute("searchType") %>" />
	</go>
	下一页
	</anchor>
	<%
		}
		if( pet_page.hasPreviousPage() )
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=pet_page.getCurrentPageNo()-1%>" />
	<postfield name="searchType" value="<%=request.getAttribute("searchType") %>" />
	</go>
	上一页
	</anchor>
	<%
		}
		if ( pet_page.getCurrentPageNo() == 1 && pet_page.getTotalPageCount() > 2) {	
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=pet_page.getTotalPageCount()  %>" />
	<postfield name="searchType" value="<%=request.getAttribute("searchType") %>" />
	</go>
	到末页
	</anchor>
	 <%} 
	  if ( pet_page.getCurrentPageNo() == pet_page.getTotalPageCount() && pet_page.getTotalPageCount() > 2 ) 
	 {	 %>
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=1  %>" />
	<postfield name="searchType" value="<%=request.getAttribute("searchType") %>" />
	</go>
	到首页
	</anchor>
	 
	 
	 
	 
	 <%} %>
	 
 <br/> 请输入您要搜索的宠物名称:<br/>
		<input name="pet_name"  type="text"  size="7"  maxlength="6" />
		&nbsp;&nbsp;&nbsp;&nbsp;
		<anchor> 
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionPetBuy.do")%>">
			<postfield name="cmd" value="n4" />
			<postfield name="pet_name" value="$pet_name" />
			<postfield name="sortType" value="time" />
			</go>
			确定
		 </anchor>
<%@include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
