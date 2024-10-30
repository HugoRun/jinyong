<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%
	String types=(String)request.getAttribute("type");
	String page_nos=(String)request.getAttribute("page_no");
	if(types==null||page_nos==null)
	{
		%>
<anchor>
返回商城<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=n0")%>" method="post">
</go>
</anchor>
		
		<%
	}
	else
	{
		%>
<anchor>
返回商城<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/mall.do?cmd=nomral")%>" method="post">
<postfield name="type" value="<%=request.getAttribute("type")%>"/>
<postfield name="page_no" value="<%=request.getAttribute("page_no")%>"/>
</go>
</anchor>
		
		<%
	}
 %>

<%@ include file="/init/init_time.jsp"%>