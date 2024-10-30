<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.web.service.player.EconomyService"%>
<%@page import="com.pm.vo.auction.AuctionVO"%>
<%@page import="com.ls.pub.util.StringUtil"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
	<%
	    RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
	    String table_type=request.getParameter("table_type");
	    EconomyService economyServcie = new EconomyService();
		long yuanbao = economyServcie.getYuanbao( roleInfo.getBasicInfo().getUPk());
		request.getSession().setAttribute("pay_type","1");
		
	%> 
	【拍卖场】<br/>
财产:<%=roleInfo.getBasicInfo().getCopper() %>灵石(仙晶<%=yuanbao%>颗)
<br/>
<%@ include file="/jsp/auction/auctionMainPage/auction_house_menu.jsp"%><br/>
<%
	List<AuctionVO> list=(List<AuctionVO>)request.getAttribute("list");
	if(list!=null&&list.size()!=0)
	{
		for(int i=0;i<list.size();i++)
		{
			AuctionVO av=list.get(i);
			String tempString=av.getPPk()!=roleInfo.getBasicInfo().getPPk()?"是您购买的物品":"没有卖出";
			//钱财
			if(av.getAuctionSell()==2&&av.getPPk()==roleInfo.getBasicInfo().getPPk())
			{
				//灵石
				if(av.getPay_type()==1)
				{
					%>
					您<%=StringUtil.isoToGB(av.getGoodsName())%>×<%=av.getGoodsNumber()  %>拍卖得到灵石：<%=av.getBuyPrice()==0?av.getGoodsPrice():av.getBuyPrice() %>颗 
					<anchor> 
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do?cmd=n3")%>">
					<postfield name="prop_id" value="<%=av.getGoodsId()%>" />
					<postfield name="auction_id" value="<%=av.getUAuctionId()%>" />
					<postfield name="w_type" value="<%=av.getAuctionType()%>" />
					<postfield name="flag" value="money"/>
					</go>
					取回
					</anchor><br/>
					<%
				}
				//仙晶
				else
				{
					%>
					您<%=StringUtil.isoToGB(av.getGoodsName())%>×<%=av.getGoodsNumber()  %>拍卖得到仙晶：<%=av.getBuyPrice()==0?av.getGoodsPrice():av.getBuyPrice()%>颗 
					<anchor> 
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do?cmd=n3")%>">
					<postfield name="prop_id" value="<%=av.getGoodsId()%>" />
					<postfield name="auction_id" value="<%=av.getUAuctionId()%>" />
					<postfield name="w_type" value="<%=av.getAuctionType()%>" />
					<postfield name="flag" value="money"/>
					</go>
					取回
					</anchor><br/>
					<%
				}
			}
			//物品
			else
			{
				%>
				<%=StringUtil.isoToGB(av.getGoodsName())%>×<%=av.getGoodsNumber()%><%=tempString%>
				<anchor> 
					<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do?cmd=n3")%>">
					<postfield name="prop_id" value="<%=av.getGoodsId()%>" />
					<postfield name="auction_id" value="<%=av.getUAuctionId()%>" />
					<postfield name="w_type" value="<%=av.getAuctionType()%>" />
					<postfield name="flag" value="goods"/>
					</go>
					取回
					</anchor><br/>
				<%
			}
		}
	}
	else
	{
		%>
		无
		<% 
	}
 %>

<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
