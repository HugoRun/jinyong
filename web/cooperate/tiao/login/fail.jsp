<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"  pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="fail" title="<s:message key = "gamename"/>">
<p>
用户验证失败!请从玩吧重新登录!<br/>
<anchor><go href="http://wap.wanba.cn/Login.aspx?GID=0DA03D28D25F4E278D3DEEC5CFDF3E32" method="get"></go>返回玩吧</anchor>
</p>
</card>
</wml>
