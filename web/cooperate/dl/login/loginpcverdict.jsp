<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"     pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="fail" title="<s:message key = "gamename"/>">
<p>
系统检测你的连接异常，导致此种情况的原因有：<br />
1.如果你使用的PC登录游戏，请使用手机登录。<br />
2.如果你使用Ucweb浏览器登录，请关闭Ucweb浏览器的“压缩中转”、“使用代理服务器”和“预读”功能。<br />
3.如果你使用Opera、Firefox等浏览器登录，请尝试使用手机自带浏览器或Ucweb浏览器登录。<br />
如仍不能解决请联系客服：021-28901353<br />
<%@ include file="/init/return_url/return_zhuanqu.jsp"%>
</p>
</card>
</wml>
