<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@page import="com.ls.pub.constant.Channel" %>
【积分商城】<br/>
亲爱的玩家您好!我是会员服务生欢欢,很高兴为您服务!<br/> 
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=vip")%>" method="get"></go>我要成为会员</anchor><br/>
★您只有获得会员资格才可进入积分商城★<br/>
【积分装备展示】<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=show&amp;c_id=5")%>" method="get"></go>【青铜套装】</anchor>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=show&amp;c_id=62")%>" method="get"></go>【白银套装】</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=show&amp;c_id=64")%>" method="get"></go>【青铜兵匣-刀】</anchor>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=show&amp;c_id=65")%>" method="get"></go>【青铜兵匣-剑】</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=show&amp;c_id=66")%>" method="get"></go>【青铜兵匣-棍】</anchor>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=show&amp;c_id=67")%>" method="get"></go>【白银兵匣-刀】</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=show&amp;c_id=68")%>" method="get"></go>【白银兵匣-剑】</anchor>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=show&amp;c_id=69")%>" method="get"></go>【白银兵匣-棍】</anchor><br/>
--------------------<br/>
<%if(GameConfig.getChannelId() == Channel.AIR){
%><anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=vipIntro&amp;type=jifen")%>" method="get"></go>★乾坤会员特权★</anchor><br/><%
}else{
	%><anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=vipIntro&amp;type=jifen")%>" method="get"></go>★铁血会员特权★</anchor><br/><%	
} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=jifenIntro")%>" method="get"></go>★折扣消费指南★</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=vip")%>" method="get"></go>我要成为会员</anchor><br/>
--------------------<br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>


		