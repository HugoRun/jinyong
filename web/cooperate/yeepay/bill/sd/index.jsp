<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" language="java" errorPage="" %>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<%
    String b_type = (String)request.getAttribute("b_type");
%>
<card id="index" title="盛大充值卡">
<p>
盛大游戏卡充值【<%=GameConfig.getYuanbaoName() %>】快速通道<br/>
您可以使用面值为5、6、10、15、30、45、50、60、100元盛大游戏充值卡为本游戏帐号充值。<br/>
请选择您要充值的骏网一卡通充值卡面值:<br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="5"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>5元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="6"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>6元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="10"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>10元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="15"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>15元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="30"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>30元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="45"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>45元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="50"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>50元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="60"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>60元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="100"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>100元
</anchor><br/>
提示:请选择与充值卡面额一致的充值类型，否则将充值失败,系统不予补偿!<br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>

