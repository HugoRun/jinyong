<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.sky.ConfigOfSky,com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
    String skyid = (String)session.getAttribute("skyid");
    String cpurl = GameConfig.getUrlOfGame()+"/returnMall.do";
    String sky_url = ConfigOfSky.getUrlOfChongzhiKB();
%>
<anchor><go href="<%=response.encodeURL("/sky/bill.do?cmd=n0")%>" method="get"></go>K币转<%=GameConfig.getYuanbaoName() %></anchor>
<anchor><go href="<%=response.encodeURL(sky_url+"?skyid="+skyid+"&amp;cpurl="+cpurl+"&amp;appid=423007&amp;f=423007")%>" method="get"></go>K币充值</anchor>