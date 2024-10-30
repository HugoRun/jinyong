<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.constant.Channel"%>
<%
	int channel_id_for_foot = GameConfig.getChannelId();

	if (channel_id_for_foot == Channel.SKY
			|| channel_id_for_foot == Channel.WANXIANG)//思凯的充值通道
	{
%>
特别说明:
<br />
1.商城购买的道具将存入您的包裹的商城栏中;
<br />
2.如果您有任何问题请立即与管理员联系,客服电话：021-28901353
<%
	} else if (channel_id_for_foot == Channel.DANGLE)//当乐的充值通道
	{
%>
说明:
<br />
1.商城购买的道具将存入您的包裹的商城栏中;
<br />
2.充值后需等待几分钟方能确认充值是否成功，请注意游戏邮箱内的充值成功提示邮件
<br />
3.客服电话：021-28901353
<%
	}
	if (request.getAttribute("enterMall") != null) {
%>
<br />
<anchor>
商城首页
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/mall.do?cmd=n0")%>">
<postfield name="type" value="12" />
</go>
</anchor>
<%
	}
%>
<%@ include file="/init/init_time.jsp"%>