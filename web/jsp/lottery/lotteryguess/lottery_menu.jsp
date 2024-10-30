<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<%  
	Date date = new Date();
	String today = request.getAttribute("today").toString();
	String yestoday = request.getAttribute("yestoday").toString();
	String allBonus = request.getAttribute("allBonus").toString();
	String todaylotterynum =	request.getAttribute("todaylotterynum").toString();
	String lotterynum =	request.getAttribute("lotterynum").toString();
	String lotteryperbonus = request.getAttribute("lotteryperbonus").toString();
	String charitybonus = request.getAttribute("charitybonus").toString();
	String sysbonustype = request.getAttribute("sysbonustype").toString();
	String sysbonusnum = request.getAttribute("sysbonusnum").toString();
	String lotteryallnum = request.getAttribute("lotteryallnum").toString();
	String lotterycharitynum = request.getAttribute("lotterycharitynum").toString();
	String prop = request.getAttribute("prop").toString();
	%>
	
第<%=today%>期竞猜<br/>
本期投注:<%=todaylotterynum %>注<br/>
本期累计银两:<%=allBonus %>  <br/>
<% 
	if (sysbonustype.equals("0") )
	{
%>
系统追加奖励:<%=sysbonusnum%>两
<%
	}
%>
<% if(!sysbonustype.equals("0")&&!prop.equals("") && prop !=null) 
	{%>
		<%= prop %>
		<%} %>
<br/>
--------------------<br/>
第<%=yestoday%>期竞猜<br/>
累计奖励银两:<%=allBonus %><br/>
正确投注:<%=lotterynum %><br/>
投中注数:<%=lotteryallnum %>注<br/>
每注奖励:<%=lotteryperbonus %>两<br/>
--------------------<br/>
本月慈善竞猜累计银两:<%=charitybonus %>两<br/>
<% if(date.getDate()==1){%>
慈善正确投注:<%=lotterycharitynum %><br/>
<%} %>
--------------------<br/>
<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="lotterytype" value= "0" />
	</go>
	我要竞猜
	</anchor><br/>
	<% if (date.getDate() == 1){%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="lotterytype" value= "1" />
	<postfield name="money" value= "0" />
	</go>
	慈善竞猜
	</anchor><br/>
	<% 
	}
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="lotterytype" value= "0" />
	</go>
	我要领奖
	</anchor><br/>
	<% if (date.getDate() == 1){%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="cmd" value="n7" />
	<postfield name="lotterytype" value= "1" />
	</go>
	慈善领奖
	</anchor>
	<br/>
	<%
	}
	%>
	<anchor>
	<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do?cmd=n6")%>"></go>
	查看排名
	</anchor><br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
