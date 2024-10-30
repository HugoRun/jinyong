<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@page import="com.ls.pub.constant.Channel" %>
★折扣消费指南★<br/>
欢迎您来到积分商城，这里是只有会员才能驾驭的上流商城，这里出售的东西都是世间珍品，在江湖中只在此处才有。<br/>
消费说明：<br/>
1.此商城仅适用积分进行消费；<br/>
<%if(GameConfig.getChannelId() == Channel.AIR){
%>2.独尊会员6折消费；无双会员7.5折消费；乾坤会员8.8折消费；<br/>
3.积分是在<%=GameConfig.getYuanbaoName() %>充值时自动累计的；<br/><%
}else{
%>2.帝王会员6折消费；贵宾会员7.5折消费；铁血会员8.8折消费；<br/>
3.积分是在<%=GameConfig.getYuanbaoName() %>充值时自动累计的；<br/><%	
} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=jifen&amp;type=7")%>" method="get"></go>返回上一级</anchor><br/>
<%@ include file="/init/inc_mall/return_mall_main.jsp"%>
</p>
</card>
</wml>


		