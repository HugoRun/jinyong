<%@page contentType="text/vnd.wap.wml"
    import="com.ls.pub.constant.Channel,com.ls.pub.config.GameConfig,com.ls.pub.util.encrypt.MD5Util,java.util.Date"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Map"%>
<%
	int channel_id = GameConfig.getChannelId();
		String backUrl=(String)request.getSession().getAttribute("backUrl");
		String backNetUrl=(String)request.getSession().getAttribute("backNetUrl");
	if (channel_id == Channel.JUU) {
		String key="3IOJ3934KJ3493KJ94K";
		String username = (String) request.getSession().getAttribute("ssid");
		String time = Long.toString(new Date().getTime() / 1000);
		String sign_bak = "51" + username + time + key;
		String sign = MD5Util.md5Hex(sign_bak);
%>
<anchor>
<go href="http://bbs.juu.cn/index.php?action=newlogin&amp;gameid=51&amp;username=<%=username%>&amp;fid=75&amp;time=<%=time%>&amp;sign=<%=sign%>" method="get">
</go>
返回论坛</anchor>-<anchor>
<go href="http://interface.juu.cn/new/no_pws_login.php?gameid=51&amp;username=<%=username%>&amp;time=<%=time%>&amp;sign=<%=sign%>" method="get"></go>
游戏专区</anchor>-<anchor>首页
<go href="http://wap.juu.cn" method="get"></go></anchor>
<br/>
请记住官网:wap.juu.cn<br/>
书签-
<anchor>在线客服<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/gmmail.do?cmd=n1")%>" method="get"></go></anchor><br/>
<%
	}
	if (channel_id == Channel.WANXIANG) {
		String zhuanqu_url = GameConfig.getUrlOfZhuanqu();
%>
<anchor>BUG反馈<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/jsp/gmmail/bugreport.jsp")%>" method="get"></go></anchor><br/>
<anchor>
<go href="<%=zhuanqu_url%>" method="get"></go>
返回游乐社区
</anchor><br/>
<%
	}
	if (channel_id == Channel.TELE) {
		String zhuanqu_url = GameConfig.getUrlOfZhuanqu();
%>
<anchor>
<go href="<%=response.encodeUrl(GameConfig.getContextPath()+"/loginout.do?cmd=n1") %>" method="get"></go>
返回上级
</anchor><br/>
<anchor>
<go href="<%=backUrl%>" method="get"></go>
返回爱游戏
</anchor><br/>
<anchor>
<go href="<%=backNetUrl%>" method="get"></go>
返回互联星空首页
</anchor><br/>
---------------------------
<%
	HashMap<String,String> recomm=(HashMap<String,String>)request.getSession().getAttribute("recomm");
	if(recomm!=null&&recomm.size()!=0)
	{
		Iterator iter=recomm.entrySet().iterator();
		while(iter.hasNext())
		{
			Map.Entry entry=(Map.Entry)iter.next();
			String key=entry.getKey().toString();
			String value=entry.getValue().toString();
			%>
			精彩推荐：<anchor><%=key%><go href="<%=value%>" method="get"></go></anchor><br/>
			<%
		}
	}
	}
%>