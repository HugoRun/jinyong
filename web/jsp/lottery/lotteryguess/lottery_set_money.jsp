<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import ="com.lw.dao.lottery.PlayerLotteryInfoDao,com.lw.vo.lottery.PlayerLotteryInfoVO" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
    <% 
    int pPk = Integer.parseInt((String)request.getSession().getAttribute("pPk"));
    int type = Integer.parseInt(request.getParameter("lotterytype").toString());
    PlayerLotteryInfoDao dao =new PlayerLotteryInfoDao();
    PlayerLotteryInfoVO vo = dao.getLotteryInfoByPpk(pPk);%>
     本月您共投注<%=vo.getLotteryNum()%>次，中奖<%=vo.getLotteryWinNum()%>次！<br/>
     投注时间为每天的8:00～20:00<br/>
     请选择您的投注大小:<br/>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="1" />
	<postfield name="cmd" value="n3" />
	</go>
	1两
	</anchor>
     
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="2" />
	<postfield name="cmd" value="n3" />
	</go>
	5两
	</anchor>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="3" />
	<postfield name="cmd" value="n3" />
	</go>
	10两
	</anchor>
    <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="4" />
	<postfield name="cmd" value="n3" />
	</go>
	20两
	</anchor>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="5" />
	<postfield name="cmd" value="n3" />
	</go>
	50两
	</anchor>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do")%>">
	<postfield name="lotterytype" value="<%=type%>" />
	<postfield name="money" value="6" />
	<postfield name="cmd" value="n3" />
	</go>
	100两
	</anchor><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/lottery.do?cmd=n1")%>"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
