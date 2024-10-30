<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="aboute" title="<s:message key = "gamename"/>">
<p>
点数帮助说明<br/>
1.游戏点数：游戏点数是中国电信天翼游戏平台为用户提供的一种游戏支付方式，<br/>用户可以使用账户中的点数来购买或消费游戏道具。<br/>
2.点数充值：每个用户在中国电信天翼游戏平台都有自己的账户，<br/>
用户可以通过花费购买点数的方式为自己的账户充值，<br/>
一元话费可购买100点点数，每月最多可购买100元即10000点。<br/>
3.点数账户：每个用户的手机号码在中国电信天翼游戏平台都有一个固定的点数账户<br/>
用户可使用账户内的点数购买游戏和在不同游戏中购买道具，点数账户并不是<br/>
用户在某款游戏中的游戏账户。<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/cooperate/tele/index/pointPage.jsp")%>" method="get"></go>返回上一级</anchor>
</p>
</card>
</wml>
