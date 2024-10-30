<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.util.*"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%   
		String hint = (String) request.getAttribute("hint");
		
	%> 
	<%
		if( hint!=null ) {
			out.println(hint);
		}
		else
		{
			out.print("宠物信息错误");
		}
	 %>
<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/petsale.do?cmd=n1")%>"></go>返回</anchor>  
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
