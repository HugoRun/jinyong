<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.wrapinfo.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.*,com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.pm.vo.auction.*"%>
<%@page import="com.ls.pub.bean.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	//因为统一起见，所以将auctionvo换成了partEquipVO
	String auction_type =(String) request.getAttribute("auction_type");
	String auction_id =(String) request.getAttribute("auction_id");
	String page_no =(String) request.getAttribute("page_no");
	String prop_id =(String) request.getAttribute("prop_id");
		String equip_display = (String)request.getAttribute("equip_display");
		%>
<%=equip_display %><br/>
		<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionGet.do?cmd=n7")%>">
		<postfield name="w_type" value="<%=auction_type %>" />
		<postfield name="prop_id" value="<%=prop_id %>" />
		<postfield name="auction_id" value="<%=auction_id %>" />
		<postfield name="page_no" value="<%=page_no %>" />
		</go>
			购买
		</anchor><br/> 
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auctionBuy.do?cmd=n2")%>">
	<postfield name="page_no" value="<%=page_no %>" />
	<postfield name="sortType" value="time" /> 
	</go>
	返回
	</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
