<%@page pageEncoding="UTF-8" isErrorPage="false" import="com.ls.pub.constant.Wrap"%>
<%@include file="/init/templete/game_head.jsp" %>
${equip_display }<br/>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/auction/auctionGoods/input_zb_price.jsp")%>">
	<postfield name="pPk" value="${equip.PPk }" />
	<postfield name="w_type" value="<%=Wrap.EQUIP %>" />
	<postfield name="pwPk" value="${pwPk }" />
	</go>
	拍卖
	</anchor><br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/auction.do")%>">
	<postfield name="pPk" value="${equip.PPk }" />
	<postfield name="cmd" value="n5" />
	<postfield name="table_type" value="<%=request.getSession().getAttribute("table_type")%>" />
	</go>
	返回
	</anchor>
<%@include file="/init/templete/game_foot.jsp" %>