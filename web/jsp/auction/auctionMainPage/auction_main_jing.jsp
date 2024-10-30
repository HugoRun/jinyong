<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.web.service.player.EconomyService"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p> 
	<%
	    RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
	    String table_type=request.getParameter("table_type");
	    EconomyService economyServcie = new EconomyService();
		long yuanbao = economyServcie.getYuanbao( roleInfo.getBasicInfo().getUPk());
		request.getSession().setAttribute("pay_type","2");
		
	%> 
	【仙晶拍卖场】<br/>
<%@ include file="/jsp/auction/auctionMainPage/auction_main_menu.jsp"%>
财产:<%=roleInfo.getBasicInfo().getCopper() %>灵石(仙晶<%=yuanbao%>颗)
<br/>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auction.do?cmd=n1")%>">
<postfield name="pay_type" value="2" />
</go>
出售
</anchor><br/>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionBuy.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="sortType" value="time" />
<postfield name="pay_type" value="2" />
</go>
竞拍
</anchor><br/>
<anchor>
<go method="get"
	href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionGet.do?cmd=n4&amp;moreOrNot=putong")%>"></go>
拍卖记录
</anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
