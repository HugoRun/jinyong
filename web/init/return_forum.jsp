<%@page contentType="text/vnd.wap.wml"
    import="com.ls.pub.constant.Channel,com.ls.pub.config.GameConfig,com.ls.pub.util.encrypt.MD5Util,java.util.Date"
    pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<%
    String forum_url = GameConfig.getUrlOfForum();
    String area_id = GameConfig.getAreaId();
    int channel_id = GameConfig.getChannelId();

    if (channel_id == Channel.SINA || channel_id==Channel.OKP) {
    } else if (channel_id == Channel.AIR) {
%>
<anchor>
<go href="http://kong.net/b/wap/t/l.jsp?bid=9397" method="get" ></go>
游戏论坛
</anchor>
<br />
<%
    } else if (channel_id == Channel.JUU) {
        String key = "3IOJ3934KJ3493KJ94K";
        String username = (String) request.getSession().getAttribute(
                "ssid");
        String time = Long.toString(new Date().getTime() / 1000);
    	String sign_bak = "51" + username + time + key;
		String sign = MD5Util.md5Hex(sign_bak);
%>
<anchor>
<go
	href="http://bbs.juu.cn/index.php?action=newlogin&amp;gameid=51&amp;username=<%=username%>&amp;fid=43&amp;time=<%=time%>&amp;sign=<%=sign%>"
	method="get" ></go>
返回论坛
</anchor>
<br/>
<%
	} else {
%>
<anchor>游戏论坛<go href="<%=response.encodeURL(forum_url+ (String) request.getSession().getAttribute("ssid")+ "&amp;areaOrder=1&amp;channel_id="+ channel_id)%>" method="get" ></go></anchor>
<%
	}
%>