<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<p>
报名条件:<br/>
等级达到60级以上的玩家可以报名参与<br/>
比赛时间:<br/>
每个月初1号至3号为报名时间4号起为比赛时间，直至剩下最后一名为冠军<br/>
规则:<br/>
1.可以使用任何游戏内道具，没有限制<br/>
2.如果您已经报名成功但是在对战表中没有显示证明您的等级最高本轮没有对手，<br/>
  在比赛开始后进入比赛场地5分钟后即可获取本轮胜利<br/>
3.如果对手在比赛规定时间内没有进场则判弃权，您直接获胜进入下一场<br/>
4.每场比赛的获胜者可以领取奖励，失败者则没有奖励 <br/>
</p>
 <anchor>
	<go method="post"
		href="/pkactive.do?cmd=n5">
	<postfield name="cmd" value="n5" />
	</go>
	返回
</anchor>
<%@ include file="/init/init_time.jsp"%>
</wml>
