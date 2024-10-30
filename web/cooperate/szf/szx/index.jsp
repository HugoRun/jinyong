<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" language="java" errorPage="" %>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%
    String b_type = (String)request.getAttribute("b_type");
%>
<card id="index" title="神州行移动充值卡充值方式">
<p>
神州行移动充值卡充值【<%=GameConfig.getYuanbaoName() %>】快速通道<br/>
充值。<br/>
请选择您要充值的神州行充值卡面值:<br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="10"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>10元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="20"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>20元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="30"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>30元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="50"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>50元
</anchor><br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="100"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>100元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="300"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>300元
</anchor>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=input") %>" method="post">
<postfield name="money" value="500"/>
<postfield name="b_type" value="<%=b_type%>"/>
</go>500元
</anchor>
<br/>
 提示:请选择与充值卡面额一致的充值类型,否则将充值失败,系统不予补偿!<br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>

