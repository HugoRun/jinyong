<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml"> 
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pm.vo.auction.*" %>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.web.service.player.EconomyService"%>
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%
	
	int auctionTypes = (Integer)request.getAttribute("w_type");
	String page_no = (String)request.getAttribute("page_no");
	String auction_id = (String)request.getAttribute("auction_id");
	AuctionVO auctionvo = (AuctionVO)request.getAttribute("auctionvo");
	String code = (String)request.getAttribute("code");
	String sortType = (String)request.getAttribute("sortType");
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
	    String table_type=request.getParameter("table_type");
	    EconomyService economyServcie = new EconomyService();
		long yuanbao = economyServcie.getYuanbao( roleInfo.getBasicInfo().getUPk());
		String payType=(String)request.getSession().getAttribute("pay_type");
		String moneyName=payType.equals("1")?"灵石":"仙晶";
 %>
【拍卖场】<br/>
财产:<%=roleInfo.getBasicInfo().getCopper() %>灵石(仙晶<%=yuanbao%>颗)
	<br/>
 		您确定以<%=auctionvo.getGoodsPrice()%><%=moneyName %>的价格拍买<%out.println(auctionvo.getGoodsName()+"×"+auctionvo.getGoodsNumber()); %>吗?<br/>
 		
 		
 					<anchor> 
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do?cmd=n1")%>">
					<postfield name="w_type" value="<%=auctionTypes %>" />
					<postfield name="auction_id" value="<%=auction_id%>" />
					<postfield name="code" value="<%=code%>" />
					</go>
					确定
					</anchor><br/>
				<% if (sortType == null || sortType.equals("")) { %>	
					<anchor>
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do?cmd=n2")%>">
					<postfield name="auctionType" value="<%=auctionTypes %>" />
					<postfield name="page_no" value="<%=page_no %>" />
					<postfield name="sortType" value="time" />
					</go>
					返回
					</anchor>
					<%} else { %>
					<anchor>
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do?cmd=n3")%>">
					<postfield name="auctionType" value="<%=auctionTypes %>" />
					<postfield name="page_no" value="<%=page_no %>" />
					<postfield name="sortType" value="<%=sortType %>" />
					<postfield name="prop_name" value="<%=request.getAttribute("prop_name") %>" />
					</go>
					返回
					</anchor>
					<%} %>
<br/>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
