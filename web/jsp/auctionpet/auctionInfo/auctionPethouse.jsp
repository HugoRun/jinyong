<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.menu.OperateMenuVO"%>
<%@page import="com.pm.vo.auctionpet.*"%>
<%@page import="com.pm.vo.constant.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="java.util.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="map" title="<s:message key = "gamename"/>">
<p>
	拍卖仓库:
	<br/>
	<%
		List goodsList = (List) request.getAttribute("goods_list");
		List moneyList = (List) request.getAttribute("money_list");
		if (goodsList != null && goodsList.size() != 0) {
			for (int i = 0; i < goodsList.size(); i++) {
				AuctionPetVO vo = (AuctionPetVO) goodsList.get(i);
				if (i == goodsList.size() - 1) {
				
	%>
	<%=StringUtil.isoToGBK(vo.getPetName())%>&nbsp;&nbsp;&nbsp;
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionPetHouse.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="petPk" value="<%=vo.getPetPk()%>" />
	<postfield name="flag" value="goods" />
	</go>
	取回
	</anchor>
	<%} else {%>
	<%=StringUtil.isoToGBK(vo.getPetName())%>&nbsp;&nbsp;&nbsp;
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionPetHouse.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="petPk" value="<%=vo.getPetPk()%>" />
	<postfield name="flag" value="goods" />
	</go>
	取回
	</anchor>
	<br/>
	<%}}}
		if (moneyList != null && moneyList.size() != 0) {
			for (int i = 0; i < moneyList.size(); i++) {
				AuctionPetVO vo = (AuctionPetVO) moneyList.get(i);
				if (i == goodsList.size() - 1) {
	%>
	<%=StringUtil.isoToGB(vo.getPetName())%>拍卖的金钱<%=MoneyUtil.changeCopperToStr(vo.getPetPrice())%>&nbsp;&nbsp;&nbsp;
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionPetHouse.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="petPk" value="<%=vo.getPetPk()%>" />
	<postfield name="flag" value="money" />
	</go>
	取回
	</anchor>
	<%} else {%>
	<%=StringUtil.isoToGB(vo.getPetName())%>拍卖的金钱<%=MoneyUtil.changeCopperToStr(vo.getPetPrice())%>&nbsp;&nbsp;&nbsp;
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath() + "/menu/auctionPetHouse.do")%>">
	<postfield name="cmd" value="n3" />
	<postfield name="petPk" value="<%=vo.getPetPk()%>" />
	<postfield name="flag" value="money" />
	</go>
	取回
	</anchor>
	<br/>
	<%}}}if ((goodsList == null && moneyList == null)|| (goodsList.size() == 0 && moneyList.size() == 0)) {out.print("无");}%>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
