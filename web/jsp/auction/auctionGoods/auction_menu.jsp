<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.constant.*"%> 
<%@ include file="/init/wrap/wrap_head.jsp"%>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n1&amp;w_type="+Wrap.BOOK)%>"></go>书卷</anchor>|
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n1&amp;w_type="+Wrap.CURE)%>"></go>药品</anchor>|
<anchor>装备
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/auction/auctionGoods/auction_list_zb.jsp")%>">
<postfield name="fenlei" value="3" />
<postfield name="table_type" value="2" />
</go>
</anchor>|
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/menu/auction.do?cmd=n1")%>"></go>任务</anchor>|
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n1&amp;w_type="+Wrap.REST)%>"></go>其他</anchor>|
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do?cmd=n1&amp;w_type="+Wrap.SHOP)%>"></go>商城</anchor>