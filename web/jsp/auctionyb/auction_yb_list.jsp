<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.pm.vo.auction.*"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*,com.pm.vo.auction.*"%>
<%@page import="com.pm.vo.constant.*,com.ls.web.service.player.*"%>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.ben.cache.dynamic.manual.user.RoleCache"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	
	<%
	RoleEntity  roleInfo = (RoleEntity)request.getAttribute("roleInfo");
	PropertyService propertyService = new PropertyService();
	QueryPage yb_page = (QueryPage)request.getAttribute("yb_page");
	List yb_list = (List)yb_page.getResult(); 
	AuctionYBVO vo = null;
	%>
您现有：<%=MoneyUtil.changeCopperToStr(roleInfo.getBasicInfo().getCopper()) %><br/>				
	 <%
	 	if( yb_list!=null && yb_list.size()!= 0)
	 	{
	 		RoleCache roleCache = new RoleCache();
	 		for( int i=0;i<yb_list.size();i++)
	 		{
	 			vo = (AuctionYBVO)yb_list.get(i);
	 			
	 			%> 
	 			 
	 			【<%=GameConfig.getYuanbaoName() %>】×<%=vo.getYbNum() %>
	 			(<%=propertyService.getPlayerName(vo.getPPk())%>)
	 			<%=MoneyUtil.changeCopperToStr(vo.getYbPrice()) %>
	 			
	 				<anchor> 
					<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do")%>">
					<postfield name="cmd" value="n2" />
					<postfield name="w_type" value="<%=vo.getUybId() %>" />
					<postfield name="page_no" value="<%=yb_page.getCurrentPageNo() %>" />
					</go>
					购买
					</anchor> 
	 				<br/>
	 				
	 			<%
	 		}
	 	}
	 	else
	 	{
	 		out.print("暂无商品<br/> ");
	 	}
	  %> 
	    <%
		if( yb_page.hasNextPage() )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=yb_page.getCurrentPageNo()+1%>" />
	</go>
	下一页
	</anchor>
	<%
		}
		if( yb_page.hasPreviousPage() )
		{  
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=yb_page.getCurrentPageNo()-1%>" />
	</go>
	上一页
	</anchor>
	<%
		}
		if ( yb_page.getCurrentPageNo() == 1 && yb_page.getTotalPageCount() > 2) {	
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=yb_page.getTotalPageCount() %>" />
	</go>
	到末页
	</anchor>
	 <%} 
	  if ( yb_page.getCurrentPageNo() == yb_page.getTotalPageCount() && yb_page.getTotalPageCount() > 2 ) 
	 {	 %>
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do?cmd=n1")%>">
	<postfield name="page_no" value="<%=1 %>" />
	</go>
	到首页
	</anchor>
	 
	 
	 
	 
	 <%} %>
	 <br/> 
	  <anchor>
	  <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/auctionyb.do")%>">
	  <postfield name="cmd" value="n5" />
	  </go>
	  拍卖<%=GameConfig.getYuanbaoName()%>
	  </anchor>
	  <br/>   
<%@ include file="/init/inc_mall/return_mall_main.jsp"%> 
</p>
</card>
</wml>
