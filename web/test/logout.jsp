<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>

<%
	String dl_url = "";
	http://*****/plaf/wml/logout-421-21- 1.gmcs?username=cccccc&timestamp=200812011200&encrypt-type=0&verify-string=34342ab56345cd
	
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="logout" title="注销">
<p>
<anchor>
<go method="post" href="<%=dl_url%>">
</go>
注销游戏账号
</anchor><br/>
</p>
</card>
</wml>
