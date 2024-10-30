<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.constant.Channel"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
您好，欢迎您进入《<%=GameConfig.getGameName() %>》,您是否是第一次玩,如果您从未玩过这款游戏,您可以选择观看新手指导.<br/>
<anchor> 
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n5") %>">
<postfield name="step" value="5" />
</go>
我是新手,现在就开始新手指导吧
</anchor>
<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/login.do?cmd=n6") %>" method="get"></go>
我不是第一次玩,不用教我
</anchor>
<%if(GameConfig.getChannelId() == Channel.AIR){
%><%=GameConfig.getGameName() %>赠送给您一个补天道具，它会使您在战斗中自动恢复气血与内力，它有3天的使用期限，当使用期限过后你可以在商城中购买获得<%
} %>
</p>
</card>
</wml>
