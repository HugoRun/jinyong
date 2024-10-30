<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>

<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="bill" title="充值">
<p>
<%
String orderId = request.getAttribute("orderId").toString();
String token = request.getAttribute("token").toString();
%>
<%@ include file="/init/system/error_hint.jsp"%>
<anchor>
<go href="http://kong.net/kx/pay/submitOrder.wml" accept-charset="UTF-8" method="post" >
<postfield name="appId" value="1961"/>
<postfield name="orderId" value="<%=orderId %>"/>
<postfield name="token" value="<%=token %>"/>
<postfield name="redirectUrl" value="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n0")%>"/>
</go>
确定支付
</anchor>
<br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
