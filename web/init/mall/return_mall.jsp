<%@page contentType="text/vnd.wap.wml"
    import="com.ls.pub.constant.Channel,com.ls.pub.config.GameConfig"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
    int channel_id = GameConfig.getChannelId();
    String exchange_hint = "";
    if (channel_id == Channel.DANGLE)//思凯的充值通道
    {
        exchange_hint = "每成功充值1元可兑换【"+GameConfig.getYuanbaoName()+"】×100!";
    } else if (channel_id == Channel.SKY) {
        exchange_hint = "每成功兑换1K币可获得【"+GameConfig.getYuanbaoName()+"】×1,并可获得商城积分×1!";
    } else if (channel_id == Channel.WANXIANG) {
        exchange_hint = "每成功充值1元可兑换【"+GameConfig.getYuanbaoName()+"】×100!";
    } else if (channel_id == Channel.TIAO) {
        exchange_hint = "每成功兑换1金豆可兑换【"+GameConfig.getYuanbaoName()+"】×100,并可获得商城积分×100!";
    } else if (channel_id == Channel.JUU) {
		exchange_hint = "每成功充值1元可获得【"+GameConfig.getYuanbaoName()+"】×100,并可获得商城积分×100!";
	} else if (channel_id == Channel.SINA) {
		exchange_hint = "每成功兑换1U币可获得【"+GameConfig.getYuanbaoName()+"】×100,并可获得商城积分×100!";
	} else if (channel_id == Channel.AIR) {
		exchange_hint = "1元民币=10元宝<br/>每成功兑换1元宝可获得【"+GameConfig.getYuanbaoName()+"】×10,并可获得商城积分×10!";
	} else if (channel_id == Channel.TXW) {
		exchange_hint = "每成功兑换1蛙元可获得【"+GameConfig.getYuanbaoName()+"】×100,并可获得商城积分×100!";
	}
%>
温馨提示:<%=exchange_hint%><br />
<%
	String cons_amigo = (String) request.getAttribute("cons_amigo");
%>
<%@ include file="/init/mall/mall_return_only.jsp"%>
<%@ include file="/init/init_timeq.jsp"%>