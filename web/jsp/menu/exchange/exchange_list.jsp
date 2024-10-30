<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page  import="com.ls.pub.util.*" %>
<%@page import="com.ls.ben.vo.menu.OperateMenuVO"%>
<%@page import="com.ls.pub.constant.MenuType"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p> 
<%
	String menu_id = (String)request.getAttribute("menu_id");
	List exchange_list = (List)request.getAttribute("exchange_list");
	List aimchange_list = (List)request.getAttribute("aimchange_list");
	String operate2 = (String)request.getAttribute("operate2");
	OperateMenuVO vo = (OperateMenuVO)request.getAttribute("menu");
	String[] gaveGoods = null;
	if(operate2 != null && operate2.length() != 0){
		gaveGoods = operate2.split("-");								
	}
	String operate3 = (String) request.getAttribute("operate3");
	
%>

<%
	if( aimchange_list!=null && aimchange_list.size()!=0 )
	{
	
		for( int i=0;i<exchange_list.size();i++ )
		{
			String aimArticle = (String) aimchange_list.get(i);
			String[] aimArticles = aimArticle.split("-");
			
			if( exchange_list!=null && exchange_list.size()!=0 )
			{
				String article = (String) exchange_list.get(i);
				String[] articles = article.split("-");
			
				%>
				<%for(int a=1;a < articles.length;a++){ %>
				<%=articles[a] %>
				<%if(a+1 != articles.length){ %>
				,<%}}} %>
				得到
				<%for(int b=0;b<aimArticles.length;b++){ 
					if(gaveGoods != null && gaveGoods.length != 0 && gaveGoods.length == 4){ %>
					
					<%if(vo!=null&&(vo.getMenuType()==MenuType.SHEARE||vo.getMenuType()==MenuType.OLD_XIANG)){ %>
					<%=aimArticles[b] %>×<%=gaveGoods[3] %>
					<%}else{ %>
						<anchor>
						<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/exchange.do")%>">
						<postfield name="cmd" value="n3" />
						<postfield name="goodsType" value="<%=gaveGoods[1] %>" />
						<postfield name="goodsId" value="<%=gaveGoods[2] %>" />
						<postfield name="menu_id" value="<%=menu_id %>" />
						</go>
						<%=aimArticles[b] %>
						</anchor>
						×<%=gaveGoods[3] %>
				<%}} else {%>
					<%=aimArticles[b] %>
				
				<%} if(b+1 != aimArticles.length){ %>
				,<%}} %>
				<anchor>
				<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/exchange.do")%>">
				<postfield name="cmd" value="n2" />
				<postfield name="address" value="0" />
				<%--postfield name="address" value="<%=//articles[0] %>" /--%>
				<postfield name="menu_id" value="<%=menu_id %>" />
				</go>
				兑换
				</anchor>
				<%if(vo!=null&&(vo.getMenuType()!=MenuType.SHEARE&&vo.getMenuType()!=MenuType.OLD_XIANG)){ %>
				<%if(operate3 != null && operate3.equals("0")) {%>
				<anchor>
				<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/menu/exchange/batchExchange.jsp")%>">
				<postfield name="menu_id" value="<%=menu_id %>" />
				</go>
				|批量兑换
				</anchor><br/>
				<%}
				}}
				}
 	else
 	{
 		%>暂无物品可兑换<% 
 	} %>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
				