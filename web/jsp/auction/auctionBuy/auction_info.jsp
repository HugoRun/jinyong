<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.pm.vo.auction.*"%> 
<%@page import="com.ls.pub.util.*"%>
<%@page import="java.util.*"%>
<%@page import="com.ls.web.service.player.RoleService"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.web.service.player.EconomyService"%>
<%@page import="com.ls.pub.bean.QueryPage"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
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
		QueryPage queryPage=(QueryPage)request.getAttribute("queryPage");
		List<AuctionInfoVO> auctionInfoList=(List<AuctionInfoVO>)queryPage.getResult();
	%> 
	【拍卖场】<br/>
财产:<%=roleInfo.getBasicInfo().getCopper() %>灵石(仙晶<%=yuanbao%>颗)<br/>
您最近的拍卖记录：<br/>
<%
		if(auctionInfoList!=null&&auctionInfoList.size()!=0)
		{
			for(int i=0;i<auctionInfoList.size();i++)
			{
				AuctionInfoVO aiv=auctionInfoList.get(i);
				out.println(aiv.getAuctionInfo()+"<br/>");
			}
			out.print(queryPage.getPageFoot());
		}
		else
		{
			out.println("无");
		}	
 %>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
