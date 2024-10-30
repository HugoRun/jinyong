<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.util.encrypt.MD5Util"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="fail" title="<s:message key = "gamename"/>">
<p>
<%
	String login_platform_url = GameConfig.getUrlOfLoginPlatform();
	String sina_uid = (String)request.getAttribute("sina_uid");
	String sina_uid_bak = (String)request.getAttribute("sina_uid_bak");
	String display = (String)request.getAttribute("display");
	String sina_uid_str = sina_uid+"sina_uid_jytiexue";
	String md5 = MD5Util.md5Hex(sina_uid_str);
	if(display != null && !display.equals("")){
	%><%=display%><br/><%
	}
%>
请输入您要关联的游客号码<br/>
<input name="passport_visitor" type="text" size="11"/><br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/vis.do?cmd=n4")%>" method="post">
<postfield name="passport_visitor" value="$passport_visitor" />
<postfield name="sina_uid" value="<%=sina_uid %>" />
<postfield name="sina_uid_bak" value="<%=sina_uid_bak %>" />
</go>
进入游戏
</anchor>
<anchor><br/>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/login.do")%>" method="post">
<postfield name="sina_uid" value="<%=sina_uid_bak %>" />
<postfield name="sign" value="<%=sina_uid_bak %>" />
<postfield name="key" value="<%=md5%>" />
<postfield name="sina_uid_bak" value="<%=sina_uid_bak %>" />
</go>
不关联直接进入游戏
</anchor><br/>
<anchor><go href="<%=login_platform_url %>" method="get"></go>返回专区</anchor>
</p>
</card>
</wml>
