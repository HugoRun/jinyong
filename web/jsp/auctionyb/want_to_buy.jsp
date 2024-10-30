<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.pm.vo.auction.*,com.ls.web.service.player.*"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*"%>
<%@page import="com.pm.vo.constant.*"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	
	<%
	PropertyService propertyService = new PropertyService();
	AuctionYBVO  auctionYBVO = (AuctionYBVO)request.getAttribute("auctionYBVO");
	String  page_no = (String)request.getAttribute("page_no");
	%>
您确定花费<%=MoneyUtil.changeCopperToStr(auctionYBVO.getYbPrice()) %>购买
<%=propertyService.getPlayerName(auctionYBVO.getPPk()) %>的【<%=GameConfig.getYuanbaoName() %>】×<%=auctionYBVO.getYbNum() %>？<br/>				
<anchor>
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do?cmd=n3")%>">
<postfield name="uybId" value="<%=auctionYBVO.getUybId() %>" />
</go>
确定
</anchor><br/>
<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do?cmd=n1")%>" >
<postfield name="page_no" value="<%=page_no %>" />
</go>
返回</anchor><br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>
