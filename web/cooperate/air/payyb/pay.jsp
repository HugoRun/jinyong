<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="bill" title="充值">
<p>
<%
String orderId = request.getAttribute("orderId").toString();
String token = request.getAttribute("token").toString();
%>
<%@ include file="/init/system/error_hint.jsp"%>
<anchor>
<go href="http://221.179.172.144:8091/kx/pay/payGateway.wml" accept-charset="UTF-8" method="post" >
<postfield name="appId" value="1961"/>
<postfield name="orderId" value="<%=orderId %>"/>
<postfield name="token" value="<%=token %>"/>
<postfield name="redirectUrl" value="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n0")%>"/>
</go>
确定支付
</anchor>
<br/>
APPID
USERID在用户系统中的ID，从用户系统获得
ORDERID
PAYMENT
REDIRECTURL
REMARK
SIGN
APPID=*****&USERID=***&ORDERID&PAYMENT&REDIRECTURL&REMARKPUBKEY
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>