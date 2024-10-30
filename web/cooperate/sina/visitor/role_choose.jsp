<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="fail" title="<s:message key = "gamename"/>">
<p>
<%
	String login_platform_url = GameConfig.getUrlOfLoginPlatform();
	String display = (String)request.getAttribute("display");
	if(display != null && !display.equals("")){
	%><%=display%><br/><%
	}
%>
请输入您的游客号或让系统生成给您新的游客号<br/>
<input name="passport_visitor" type="text" size="11"/><br/>
<anchor>
<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/vis.do?cmd=n3")%>" method="post">
<postfield name="passport_visitor" value="$passport_visitor" />
</go>
确认
</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/sina/vis.do?cmd=n3")%>" method="get"></go>生成新的游客号</anchor><br/>
<anchor><go href="<%=login_platform_url %>" method="get"></go>返回专区</anchor>
</p>
</card>
</wml>