<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"  "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ls.pub.bean.*"%>
<%@page import="java.util.*,com.ben.vo.info.partinfo.PartInfoVO"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	
	<%
		String hint = (String)request.getAttribute("hint");
		String return_type = (String)request.getAttribute("return_type");
		out.println(hint+"<br/>");
		if(return_type.equals("2")) {
	%>	
<anchor>返回<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do?cmd=n1") %>"></go></anchor>
	<%} else if ( return_type.equals("1")) { %>
		<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n2")%>">
		</go>
		返回
		</anchor>
	
	
	<%} %>	
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
