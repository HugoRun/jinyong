<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<%
	String resultWml = (String)request.getAttribute("resultWml");
	int type = Integer.parseInt(request.getAttribute("resultWml").toString());
	if( resultWml!=null )
	{
%> 
<%=resultWml%>
<%
	}
	else
	{
		out.print("空指针<br/>");
	}
 %> 
<% if(type == 1){
%>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/addPetStorage.do?cmd=n1") %>"></go>返回</anchor>
<%
}else{
%>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getPetStorage.do?cmd=n1") %>"></go>返回</anchor>
<%
} %>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
 