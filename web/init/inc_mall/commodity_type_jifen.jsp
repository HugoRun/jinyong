<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %>  
积分商城道具:<br/>
<anchor>装备
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=jifenmall")%>">
<postfield name="type" value="1"/>
</go></anchor>
|
<anchor>祝福
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=jifenmall")%>">
<postfield name="type" value="2"/>
</go></anchor>
|
<anchor>氏族
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=jifenmall")%>">
<postfield name="type" value="4"/>
</go></anchor>
<br/>
<anchor>返回商城首页
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n0")%>">
<postfield name="type" value="12"/>
</go>
</anchor><br/>