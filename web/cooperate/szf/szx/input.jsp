<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" language="java" errorPage="" %>
<%
	String money = (String)request.getParameter("money");
	String b_type = (String)request.getAttribute("b_type");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="index" title="神州行移动充值卡充值方式">
<p>
神州行移动充值卡充值【<%=GameConfig.getYuanbaoName() %>】快速通道<br/>
您选择了使用<%=money %>元面值的神州行移动充值卡为本游戏帐号充值<br/>
请输入您的充值卡卡号:<br/>
<input name="code" maxlength="17"></input>(17位数字)<br/>
请输入您的充值卡密码:<br/>
<input name="psw" maxlength="18"></input>(18位数字)<br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=comfirm") %>" method="post">
<postfield name="pay" value="<%=money %>"/>
<postfield name="code" value="$code"/>
<postfield name="psw" value="$psw"/>
<postfield name="b_type" value="<%=b_type %>"/>
</go>立即充值
</anchor><br/>
帮助信息<br/>
1.本服务支持10、20、30、50、100、300、500元面值神州行移动充值卡<br/>
2.神州行移动充值卡卡号为17位，密码为18位<br/>
3.您可以在全国各地报刊亭,网吧等购买神州行移动充值卡为本游戏充值.<br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>