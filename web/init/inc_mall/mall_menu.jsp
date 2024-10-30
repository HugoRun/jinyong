<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<anchor>快速充值<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/sky/bill.do?cmd=n0")%>" method="get" ></go></anchor>
|
<anchor><%=GameConfig.getYuanbaoName() %>拍卖<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/auctionyb.do?cmd=n1")%>" method="get" ></go></anchor>
|
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/mall.do?cmd=n7")%>" method="get" ></go>
点数查询
</anchor>