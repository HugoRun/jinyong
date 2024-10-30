<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pm.vo.constant.*"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.web.service.player.EconomyService"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%@page import="com.pm.vo.auction.AuctionVO"%>
<%@page import="com.ls.pub.util.StringUtil"%>
<%@page import="com.ls.pub.util.MoneyUtil"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<%
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
	    String table_type=request.getParameter("table_type");
	    EconomyService economyServcie = new EconomyService();
		long yuanbao = economyServcie.getYuanbao( roleInfo.getBasicInfo().getUPk());
		String pay_type=(String)request.getSession().getAttribute("pay_type");
		String moneyType=pay_type.equals("1")?"灵石":"仙晶";
 %>	 
【拍卖场】<br/>
财产:<%=roleInfo.getBasicInfo().getCopper() %>灵石(仙晶<%=yuanbao%>颗)<br/>
请选择您要查询的物品类型！<br/>
<%@ include file="/jsp/auction/auctionMainPage/auction_general_menu1.jsp"%><br/>
 <%
 		QueryPage arm_page = (QueryPage)request.getAttribute("arm_page");
		List arm_list = (List)arm_page.getResult();
		String type=(String)request.getAttribute("type");
		String mtype=(String)request.getAttribute("mtype");
		AuctionVO vo = null;
	 	if( arm_list!=null && arm_list.size()!= 0)
	 	{
	 		for( int i=0;i<arm_list.size();i++)
	 		{
	 			vo = (AuctionVO)arm_list.get(i);
	 			String methodStr=vo.getAuctionType()==4?"n5":"n6";
	 			String displayName=vo.getAuctionType()==4?StringUtil.isoToGB(vo.getGoodsName())+"X"+vo.getGoodsNumber():StringUtil.isoToGB(vo.getGoodsName());
	 			%> 
	 				<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do")%>">
					<postfield name="cmd" value="<%=methodStr %>" />
					<postfield name="auction_type" value="<%=AuctionType.ARM%>" />
					<postfield name="prop_id" value="<%=vo.getGoodsId()%>" />
					<postfield name="auction_id" value="<%=vo.getUAuctionId()%>" />
					<postfield name="page_no" value="<%=arm_page.getCurrentPageNo() %>" />
					</go>
	 					<%= displayName%>
	 				</anchor>|<%=vo.getGoodsPrice()%><%=moneyType %>|<anchor> 
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do?cmd=n7")%>">
					<postfield name="page_no" value="<%=arm_page.getCurrentPageNo() %>" />
					<postfield name="w_type" value="<%=AuctionType.ARM%>" />
					<postfield name="prop_id" value="<%=vo.getGoodsId()%>" />
					<postfield name="auction_id" value="<%=vo.getUAuctionId()%>" />
					<postfield name="type" value="1" />
					</go>
					一口价
					</anchor>|<%=vo.getAuction_price()%><%=moneyType %>|<anchor> 
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do?cmd=n7")%>">
					<postfield name="page_no" value="<%=arm_page.getCurrentPageNo() %>" />
					<postfield name="w_type" value="<%=AuctionType.ARM%>" />
					<postfield name="prop_id" value="<%=vo.getGoodsId()%>" />
					<postfield name="auction_id" value="<%=vo.getUAuctionId()%>" />
					<postfield name="type" value="2" />
					</go>
					竞拍价
					</anchor><br/>
	 				
	 			<%
	 		}
	 	}
	 	else
	 	{
	 		out.print("无<br/>");
	 	}
	  %> 
	    <%
		if( arm_page.hasNextPage() )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do?cmd="+mtype)%>">
	<postfield name="auctionType" value="<%=type%>" />
	<postfield name="page_no" value="<%=arm_page.getCurrentPageNo()+1%>" />
	<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
	</go>
	下一页
	</anchor>
	<%
		}
		if( arm_page.hasPreviousPage() )
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do?cmd="+mtype)%>">
	<postfield name="auctionType" value="<%=type%>" />
	<postfield name="page_no" value="<%=arm_page.getCurrentPageNo()-1%>" />
	<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
	</go>
	上一页
	</anchor>
	<%
		}if ( arm_page.getCurrentPageNo() == 1 && arm_page.getTotalPageCount() > 2) {	
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do?cmd="+mtype)%>">
	<postfield name="auctionType" value="<%=type%>" />
	<postfield name="page_no" value="<%=arm_page.getTotalPageCount() %>" />
	<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
	</go>
	到末页
	</anchor>
	 <%} 
	 if ( arm_page.getCurrentPageNo() == arm_page.getTotalPageCount() && arm_page.getTotalPageCount() > 2 ) 
	 {	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do?cmd=+mtype")%>">
	<postfield name="auctionType" value="<%=type%>" />
	<postfield name="page_no" value="<%=1 %>" />
	<postfield name="sortType" value="<%=request.getAttribute("sortType") %>" />
	</go>
	到首页
	</anchor>	 
	<%} 
		if (arm_page.getTotalPageCount() != 0) {
			out.println(arm_page.getCurrentPageNo()+"/"+arm_page.getTotalPageCount());
		} else {
			out.println("0/0");
		}
	 %>
	 <br/>
--------------------------<br/>
请输入您要搜索的物品名称:<br/>
		<input name="prop_name"  type="text" size="8"  maxlength="8" />
		<anchor> 
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do")%>">
			<postfield name="cmd" value="n3" />
			<postfield name="prop_name" value="$prop_name" />
			<postfield name="sortType" value="time" />
			<postfield name="auctionType" value="<%=type%>"/>
			</go>
			确定
		 </anchor><br/>
		 <anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/jsp/auction/auctionMainPage/auction_main_shi.jsp")%>" method="get"></go>
	返回
	</anchor>  
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
