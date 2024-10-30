<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8" language="java" errorPage="" %>
<%
    String money = (String)request.getParameter("money");
    String b_type = (String)request.getAttribute("b_type");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="index" title="骏网一卡通充值方式">
<p>
骏网一卡通充值卡充值【<%=GameConfig.getYuanbaoName() %>】快速通道<br/>
您选择了使用<%=money %>元面值的骏网一卡通充值卡为本游戏帐号充值<br/>
请输入您的充值卡卡号:<br/>
<input name="code" maxlength="16"></input>(16位数字)<br/>
请输入您的充值卡密码:<br/>
<input name="psw" maxlength="16"></input>(16位数字)<br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/bill.do?cmd=comfirm") %>" method="post">
<postfield name="pay" value="<%=money %>"/>
<postfield name="code" value="$code"/>
<postfield name="psw" value="$psw"/>
<postfield name="b_type" value="<%=b_type %>"/>
</go>立即充值
</anchor><br/>
帮助信息<br/>
1.本服务支持5、6、10、15、30、50、60、100元面值骏网一卡通充值卡<br/>
2.骏网一卡通充值卡卡号为16位，密码为16位<br/>
3.您可以在全国各地报刊亭,网吧等购买骏网一卡通充值卡为本游戏充值.<br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
