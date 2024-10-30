<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"  pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.util.encrypt.MD5Util,java.util.Date" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="fail" title="<s:message key = "gamename"/>">
<p>
<%
	String key = "3IOJ3934KJ3493KJ94K";
	String username = (String)request.getSession().getAttribute("ssid");
	String time = Long.toString(new Date().getTime()/1000);
	String sign = MD5Util.md5Hex("51"+username+time+key);
%>
用户验证失败!请从聚友网重新登录!<br/>
<anchor><go href="http://interface.juu.cn/new/no_pws_login.php?gameid=51&amp;username=<%=username%>&amp;time=<%=time%>&amp;sign=<%=sign%>" method="get"></go>返回专区</anchor>
</p>
</card>
</wml>
