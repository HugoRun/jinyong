<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.pub.*,com.ben.dao.wrapinfo.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.constant.*,com.ls.web.service.player.*"%>
<%@page import="com.ls.pub.util.*,com.ls.model.user.*"%>
<%@page import="com.ls.web.service.player.EconomyService"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerWrapVO"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<% 
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		RoleService roleService = new RoleService();
		RoleEntity roleInfo = roleService.getRoleInfoBySession(session);
		String pPk = roleInfo.getBasicInfo().getPPk() + "";
		WrapinfoDAO dao1 = new WrapinfoDAO();
	    PartInfoVO vo1 = (PartInfoVO)dao1.geTsilver(pPk);
	    String table_type=request.getParameter("table_type");
	    EconomyService economyServcie = new EconomyService();
		long yuanbao = economyServcie.getYuanbao( roleInfo.getBasicInfo().getUPk());
	%>
	【拍卖场】
	<br />
	财产:<%=roleInfo.getBasicInfo().getCopper()%>灵石(仙晶<%=yuanbao%>颗)
	<br />
	<%@ include
		file="/jsp/auction/auctionMainPage/auction_general_menu.jsp"%><br />

	<%
		QueryPage queryPage=(QueryPage)request.getAttribute("queryPage");
		List equip_list = (List)queryPage.getResult();
		if (equip_list != null && equip_list.size() != 0) {
			for (int i =0; i <equip_list.size(); i++) {
				PlayerEquipVO partEquipVO = (PlayerEquipVO) equip_list.get(i);
	%>
			<anchor>
			<go method="post"
				href="<%=response.encodeURL(GameConfig.getContextPath()
											+ "/menu/auction.do")%>">
			<postfield name="cmd" value="n4" />
			<postfield name="pwPk" value="<%=partEquipVO.getPwPk()%>" />
			<postfield name="table_type" value="<%=table_type%>" />
			</go>
			<%=partEquipVO.getFullName()%>
			</anchor>
			|
			<anchor>
			<go method="post"
				href="<%=response.encodeURL(GameConfig.getContextPath()+ "/jsp/auction/auctionGoods/input_zb_price.jsp")%>">
			<postfield name="backType" value="<%=table_type%>" />
			<postfield name="pageNum" value="<%=queryPage.getCurrentPageNo()%>" />
			<postfield name="w_type" value="<%=Wrap.EQUIP%>" />
			<postfield name="pwPk" value="<%=partEquipVO.getPwPk()%>" />
			</go>
			拍卖
			</anchor>
		
			<br/>

	<%
		}
		out.print(queryPage.getPageFoot());
	%>
	<br />
	<%
		} else {
			out.print("无");%><br/><%
		}
	%>
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()+ "/jsp/auction/auctionMainPage/auction_main_shi.jsp")%>"
		method="get"></go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>

