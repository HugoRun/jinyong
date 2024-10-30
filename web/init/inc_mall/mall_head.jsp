<%@page pageEncoding="UTF-8" isErrorPage="false"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.constant.Channel"%>
您的【<%=GameConfig.getYuanbaoName() %>】余额${yuanbao },积分${jifen }<br/>
<!--<%@ include file="/init/inc_mall/mall_menu.jsp"%><br/>
请选择你要进入的商城!<br/>-->
<%
 	String enterMall=(String)request.getAttribute("enterMall");
 	if(enterMall!=null)
 	{
 		%>
 		进入商城|
		<anchor>快速充值<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/sky/bill.do?cmd=n0")%>" method="get" ></go></anchor>
		<%
			if(GameConfig.getChannelId()!=Channel.TELE)
			{
				%>
				|
		
		<anchor><%=GameConfig.getYuanbaoName() %>拍卖<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/auctionyb.do?cmd=n1")%>" method="get" ></go></anchor>
				<%
			}
			if(GameConfig.getChannelId()==Channel.TELE)
			{
				%>
				|
		<anchor>
		<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mall.do?cmd=n7")%>" method="get" ></go>
		点数查询
		</anchor>
				<%
			}
		 %>
		
		<br/>
 		<%
 	}
 	else
 	{
 		%>
 		<anchor>进入商城<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mall.do?cmd=nomral")%>" method="get" ></go></anchor>|
		<anchor>快速充值<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/sky/bill.do?cmd=n0")%>" method="get" ></go></anchor>
		<%
			if(GameConfig.getChannelId()!=Channel.TELE)
			{
				%>
				|
		<anchor><%=GameConfig.getYuanbaoName() %>拍卖<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/auctionyb.do?cmd=n1")%>" method="get" ></go></anchor>
				<%
			}
			if(GameConfig.getChannelId()==Channel.TELE)
			{
				%>
						 |
		<anchor>
		<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mall.do?cmd=n7")%>" method="get" ></go>
		点数查询
		</anchor>
				
				<%
			}
		 %>
		<br/>
 		<%
 	}
 %>



