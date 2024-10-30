<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
警告:5级以上玩家可参与竞猜,未满5级的玩家请在师父的陪同下观看!<br/>
竞猜时间:全天.每隔60分钟开奖一次,开奖前1分钟下注截止,开奖后自动开始下一回合.<br/>
开奖事项:点开奖!开奖后24小时内领取奖项,如未能及时领取,则过期作废,不得补领!<br/>
竞猜规则:<br/>
1.点击主菜单的导航菜单“彩票”,可进入购买彩票.<br/>
2.请在4组麻将牌中,每组选择一张牌（共四张牌）进行下注.<br/>
3.当开奖后按照中奖规则进行领奖.<br/>
4.牌组:第一组:一到九筒;第二组:一到九条;第三组:一到九万;第四组:东风、南风、西风、北风、红中、白板、发财.<br/>
中奖规则:<br/>
4个号码全中,获得一等奖金<br/>
3个号码中,获得二等奖金<br/>
2个号码中,获得三等奖金<br/>
奖金说明:<br/>
三等奖每注奖金:88<%=GameConfig.getYuanbaoName() %>;<br/>
二等奖每注奖金:5000<%=GameConfig.getYuanbaoName() %>;<br/>
一等奖每注奖金:（奖池奖金-二等奖金-三等奖金）/总中奖注数;<br/>
特别说明:一等奖金每注奖金上限 = 50万;<br/>
竞猜事项:<br/>
1.投注的货币为元宝,每注为50<%=GameConfig.getYuanbaoName() %>;<br/>
2.每期只能投注一次,一次可选择多注投注;<br/>
3.所有中奖者按中奖级别分得奖池奖金;<br/>
4.奖池奖金由每期的销售额，超过单注奖金上限的奖金及系统追加组成;<br/>
5.获奖玩家抽取8.8%的税金.<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/newlottery.do?cmd=n1")%>"></go>返回竞猜</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>