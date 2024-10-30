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
String sina_uid = request.getAttribute("sina_uid").toString();
String sina_amt = request.getAttribute("sina_amt").toString();
String pay_record_id = request.getAttribute("pay_record_id").toString();
String hashStr = request.getAttribute("hashStr").toString();
String time = request.getAttribute("time").toString();
%>
<%@ include file="/init/system/error_hint.jsp"%>
<anchor>
<go href="http://3g.sina.com.cn/dpool/paycenter/payRcv.php" accept-charset="UTF-8" method="post" >
<postfield name="uid" value="<%=sina_uid%>"/>
<postfield name="pid" value="jygame"/>
<postfield name="amount" value="<%=sina_amt%>"/>
<postfield name="snum" value="<%=pay_record_id%>"/>
<postfield name="tm" value="<%=time%>"/>
<postfield name="hs" value="<%=hashStr%>"/>
<postfield name="cbUrl" value="<%=response.encodeURL(GameConfig.getChongzhiPath()+"/sina/callback.do")%>"/>
<postfield name="backUrl" value="<%=response.encodeURL(GameConfig.getChongzhiPath()+"/mall.do?cmd=n0")%>"/>
<postfield name="backTitle" value=""/>
<postfield name="item" value="<%=GameConfig.getYuanbaoName() %>"/>
<postfield name="info" value=""/>
</go>
确定支付
</anchor>
<br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
