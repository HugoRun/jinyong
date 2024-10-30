<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.rank.model.RankConstants"%>
<%
    String type = (String) request.getAttribute("type");
	int t = 0;
	if (type != null && !"".equals(type.trim())) {
		t = Integer.parseInt(type.trim());
	}
%>
<%if(t==0||t==1){ %>
【个人排行榜】<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.SHENG %>" /></go>江湖榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.DENGJI %>" /></go>等级榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.YUANBAO %>" /></go>富豪榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.MONEY %>" /></go>金银榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.CHONGWU %>" /></go>宠物榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.PET %>" /></go>狂兽榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.KILL %>" /></go>杀手榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.OPEN %>" /></go>开光榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.VIP %>" /></go>尊贵榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.SHENBING %>" /></go>神兵榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.LOST %>" /></go>探险榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.XIANJIA %>" /></go>仙甲榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.FABAO %>" /></go>法宝榜</anchor><br/>
<%} %>
<%if(t==0||t==4){ %>
【活动排行榜】<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.KILLLANG %>" /></go>神捕榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.LANGJUN %>" /></go>生存榜</anchor><br/>
<%} %>
<%if(t==0||t==2){ %>
【战斗排行榜】<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.BANGKILL %>" /></go>屠血榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.KILLNPC %>" /></go>杀怪榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.EVIL %>" /></go>恶人榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.DEAD %>" /></go>死亡榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.KILLBOSS %>" /></go>击杀榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.MENG %>" /></go>猛将榜</anchor><br/>
<%} %>
<%if(t==0||t==3){ %>
【社交排行榜】<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.CREDIT %>" /></go>声望榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.ZHONG %>" /></go>忠贞榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.YI %>" /></go>义气榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.DEAR %>" /></go>爱情榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.ANS %>" /></go>神算榜</anchor>|<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.SALE %>" /></go>生意榜</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n4")%>" method="post"><postfield name="field" value="<%=RankConstants.GLORY %>" /></go>荣誉榜</anchor><br/>
<%} %>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/rank.do?cmd=n1")%>" method="post">
<postfield name="field" value="<%=RankConstants.COME_FIELD %>" /></go>返回排行</anchor><br/>
<anchor>
<go	href="<%=response.encodeURL(GameConfig.getContextPath()+ "/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
