<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
${prop_wml }
<c:choose>
<c:when test="${discount==-1}">
售价:${original_price }积分<br/>
</c:when>
<c:otherwise>
售价:${discount_price }积分(原价:${original_price }积分)<br/>
</c:otherwise>
</c:choose>
<anchor>购买<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=vip")%>" method="get"></go></anchor><br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>


		