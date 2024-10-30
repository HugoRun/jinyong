<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="ID" title="<s:message key = "gamename"/>">
<p>
<%
	  String display = (String)request.getAttribute("display");
%>
一周内在线时间达到210分钟以上（每天30分钟）即可免费领取工资<br/>
工资每周领取一次，本周一～周日领上一周的工资，不累加<br/>
在线时间越高、等级越高领取的工资越高<br/>
<%=display %><br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/laborage.do?cmd=n2")%>"></go>领取工资</anchor><br/>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
