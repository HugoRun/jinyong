<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"  pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.util.encrypt.MD5Util,java.util.Date" %>
<%
    response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="fail" title="<bean:message key="gamename"/>">
<p>

用户验证失败!请从大家网重新登录!<br/>
<anchor><go href="#"/>返回专区</anchor>
</p>
</card>
</wml>
