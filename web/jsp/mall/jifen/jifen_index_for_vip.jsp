<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
【积分商城】<br/>
${role_title }您好!我是积分服务生欢欢,很高兴为您服务!<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sky/bill.do?cmd=n0")%>" method="get"></go>赚积分</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=jifenIntro")%>" method="get"></go>★折扣消费指南★</anchor><br/>
您在这里消费折扣:${vip_discount }
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=vip")%>" method="get"></go>我想获得折扣</anchor>!<br/>
<%@ include file="/jsp/mall/commodity_for_page.jsp"%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sky/bill.do?cmd=n0")%>" method="get"></go>赚积分</anchor><br/>
-----------------<br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>


		