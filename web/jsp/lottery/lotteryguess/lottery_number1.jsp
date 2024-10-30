<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
    <% 
       int type = Integer.parseInt(request.getParameter("lotterytype").toString());
       int money = Integer.parseInt(request.getParameter("money").toString());%>
     请从下面每组麻将花牌中选择一种花色，选中全部四种花色则中奖<br/> 
     第一组:
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/lottery/lotteryguess/lottery_number2.jsp")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="<%=money%>" />
	<postfield name="number1" value="<%="东风"%>" />
	</go>
	东风
	</anchor>
     
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/lottery/lotteryguess/lottery_number2.jsp")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="<%=money%>" />
	<postfield name="number1" value="<%="南风"%>" />
	</go>
	南风
	</anchor>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/lottery/lotteryguess/lottery_number2.jsp")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="<%=money%>" />
	<postfield name="number1" value="<%="西风"%>" />
	<postfield name="cmd" value="lottery_number_two" />
	</go>
	西风
	</anchor>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/lottery/lotteryguess/lottery_number2.jsp")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="<%=money%>" />
	<postfield name="number1" value="<%="北风"%>" />
	</go>
	北风
	</anchor> 
	<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do?cmd=n1")%>"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
