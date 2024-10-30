<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
    <%
       int type = Integer.parseInt(request.getParameter("lotterytype").toString());
       int money = Integer.parseInt(request.getParameter("money").toString());
       String number1 = (String)(request.getParameter("number1"));
       String number2 = (String)(request.getParameter("number2"));%>
     第三组:
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/lottery/lotteryguess/lottery_number4.jsp")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="<%=money%>" />
	<postfield name="number1" value="<%=number1%>" />
	<postfield name="number2" value="<%=number2%>" />
	<postfield name="number3" value="<%="春"%>" />
	</go>
	春
	</anchor>
     
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/lottery/lotteryguess/lottery_number4.jsp")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="<%=money%>" />
	<postfield name="number1" value="<%=number1%>" />
	<postfield name="number2" value="<%=number2%>" />
	<postfield name="number3" value="<%="夏"%>" />
	</go>
	夏
	</anchor>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/lottery/lotteryguess/lottery_number4.jsp")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="<%=money%>" />
	<postfield name="number1" value="<%=number1%>" />
	<postfield name="number2" value="<%=number2%>" />
	<postfield name="number3" value="<%="秋"%>" />
	</go>
	秋
	</anchor>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/lottery/lotteryguess/lottery_number4.jsp")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="<%=money%>" />
	<postfield name="number1" value="<%=number1%>" />
	<postfield name="number2" value="<%=number2%>" />
	<postfield name="number3" value="<%="冬"%>" />
	</go>
	冬
	</anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do?cmd=n1")%>"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
