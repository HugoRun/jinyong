<%@page contentType="text/vnd.wap.wml"
	import="com.ls.pub.constant.Channel,com.ls.pub.config.GameConfig,com.ls.pub.util.encrypt.MD5Util,java.util.Date"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
	int channel_id = GameConfig.getChannelId();

	if (channel_id == Channel.SKY)//思凯的充值通道,不需要返回思凯平台
	{
		String zhuanqu_url = GameConfig.getUrlOfZhuanqu();
		String ssid = (String) session.getAttribute("ssid");
%>
<anchor>
<go href="<%=zhuanqu_url + ssid%>" method="get" ></go>
返回专区
</anchor>
<%
	} else if (channel_id == Channel.DANGLE) {
		String login_params = (String) session
				.getAttribute("login_params");
		if (login_params != null && !login_params.equals("")) {
			String zhuanqu_url = GameConfig.getUrlOfZhuanqu();
%>
<anchor>
<go href="<%=zhuanqu_url + "/gp/dl/login.do?"
							+ login_params%>"
	method="get" ></go>
返回选区页面
</anchor>
<br />
<%
	}
%>
<anchor>
<go href="http://189hi.cn/plaf/wml/gacs.jw?act=gogame&amp;gameid=10"
	method="get" ></go>
返回当乐专区
</anchor>
<%
	} else if (channel_id == Channel.TIAO) {
		String login_params = (String) session
				.getAttribute("login_params");
		if (login_params != null && !login_params.equals("")) {
			String zhuanqu_url = GameConfig.getUrlOfLoginServer();
%>
<anchor>
<go href="<%=zhuanqu_url + "/tiao/login.do?"
							+ login_params%>" method="get" ></go>
返回选区页面
</anchor>
<br />
<%
	}
	} else if (channel_id == Channel.WANXIANG) {
		String login_params = (String) session
				.getAttribute("login_params");
		String zhuanqu_url = GameConfig.getUrlOfZhuanqu();
%>
<anchor>
<go href="<%=zhuanqu_url%>" method="get" ></go>
返回游乐
</anchor>
<br />
<%
	} else if (channel_id == Channel.SINA) {
%>
<anchor>
<go href="http://3g.sina.com.cn/game/s/3g/index.php?tid=60&amp;did=43"
	method="get" ></go>
返回专区
</anchor>
<br />
<%
	} else if (channel_id == Channel.JUU) {
		String key = "3IOJ3934KJ3493KJ94K";
		String username = (String) request.getSession().getAttribute(
				"ssid");
		String time = Long.toString(new Date().getTime() / 1000);
		String sign = MD5Util.md5Hex("51" + username + time + key);
%>
<anchor>
<go
	href="http://interface.juu.cn/new/no_pws_login.php?gameid=51&amp;username=<%=username%>&amp;time=<%=time%>&amp;sign=<%=sign%>"
	method="get" ></go>
返回专区
</anchor>
<br />
<%
	} else if (channel_id == Channel.YOUVB) {
		String xsid = (String) request.getSession()
				.getAttribute("xsid");
%><anchor>
<go href="http://youvb.cn/txtl.jsp?xsid=<%=xsid%>"
	method="get" ></go>
返回专区
</anchor>
<br />
<%
	} else if (channel_id == Channel.AIR) {
String zhuanqu_url = GameConfig.getUrlOfZhuanqu();
%><anchor>
<go href="<%=zhuanqu_url%>"
method="get" ></go>
查看更多上方小贴士
</anchor>
<br />
<%
	} else if (channel_id == Channel.OKP) {
	String osid = (String) session.getAttribute("osid");
%><anchor>
<go href="http://wap.wapok.cn/gogame.php?tid=12&amp;osid=<%=osid %>"
method="get" ></go>
返回官网
</anchor>
<br />
<%
}
%>