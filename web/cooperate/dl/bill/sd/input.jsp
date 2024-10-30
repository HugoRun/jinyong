<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"  pageEncoding="UTF-8" errorPage="" %>
<%
    String money = (String)request.getParameter("money");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="index" title="盛大充值卡">
<p>
盛大游戏卡充值【<%=GameConfig.getYuanbaoName() %>】快速通道<br/>
您选择了使用<%=money %>元面值的盛大游戏卡为本游戏帐号充值<br/>
请输入您的充值卡卡号:<br/>
<input name="code" maxlength="15"></input>(15位)<br/>
请输入您的充值卡密码:<br/>
<input name="psw" maxlength="8"></input>(8位)<br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/yeepay/comfirm.do") %>" method="post">
            <postfield name="pay" value="<%=money %>"/>
            <postfield name="code" value="$code"/>
            <postfield name="psw" value="$psw"/>
            <postfield name="pd_FrpId" value="SNDACARD"/>
</go>立即充值
</anchor><br/>
帮助信息<br/>
1.本服务支持5、6、10、15、30、45、50、60、100元盛大游戏充值卡<br/>
2.盛大游戏充值卡卡号为15位，密码为8位<br/>
3.您可以在全国各地报刊亭,网吧等购买盛大游戏卡为本游戏充值.<br/>
<%@ include file="/init/mall/return_mall.jsp"%>
</p>
</card>
</wml>
