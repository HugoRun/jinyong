<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.config.GameConfig" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="fail" title="<s:message key = "gamename"/>">
<p>
点数专区<br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/sky/bill.do?cmd=n0")%>" method="get" ></go>账户充值</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/cooperate/tele/index/viewPoint.jsp")%>" method="get"></go>余额查询</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/cooperate/tele/index/pointHelp.jsp")%>" method="get"></go>点数帮助</anchor><br/>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/loginout.do?cmd=n1")%>" method="get"></go>返回上级</anchor><br/>
</p>
</card>
</wml>
