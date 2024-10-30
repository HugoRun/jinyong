<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="s"%>
<card id="login" title="<s:message key = "gamename"/>">
<p>

	在广大武侠迷的热切期盼下，2009年3月18日12点整新金庸群侠传全新上市，并改名为“
	<s:message key="gamename" />
	”直接开始公测，新的世界在等着我们去创造！
	<br />
	此次公测为不删档公测，我们努力为广大玩家打造一个热血沸腾的武侠世界！
	<s:message key="gamename" />
	从封测开始就凭借着丰富而经典的武侠故事情节、健全的系统、方便的操作、积极的互动吸引着广大游戏爱好者，虽然有过挫折，但玩家的热情和关爱就是我们无尽的动力！
	<br />
	此次公测除了推出装备升级、博彩、免费工资和副本等精彩内容外，为了回馈广大玩家长久以来一直对
	<s:message key="gamename" />
	的支持，只要是在3月18日12点～3月22日24点注册并创建角色的玩家即可获得价值108<%=GameConfig.getYuanbaoName() %>的公测红包，除此以外我们特意准备了多种精彩活动让大家在游戏时更加轻松更加有趣，其中包括双倍经验双倍掉宝大放送、新区开服疯狂大冲级、公测大礼包八折大优惠等精彩活动。活动具体详情请留意游戏相关公告！
	<br />
	3月18日的公测，玩家可从当乐189hi娱乐平台
	<s:message key="gamename" />
	专区直接使用189hi平台帐号密码登录！
	<br />
	<anchor>
	<go
		href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/cooperate/dl/login/login.jsp")%>"
		method="get">
	</go>
	返回
	</anchor>
</p>
</card>
</wml>
