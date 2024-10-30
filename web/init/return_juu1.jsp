<%@page contentType="text/vnd.wap.wml"
        import="com.ls.pub.constant.Channel,com.ls.pub.config.GameConfig,com.ls.pub.util.encrypt.MD5Util,java.util.Date"
        pageEncoding="UTF-8" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%
    int channel_idd = GameConfig.getChannelId();
    if (channel_idd == Channel.JUU) {
%>
<br/>
    <anchor>
    <go href="http://www.miibeian.gov.cn/" method="get"></go>
    京ICP备09113349号
</anchor>
<%
    }
%>

