<%@page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.constant.*"%>
【乾坤袋】<anchor>增大乾坤袋<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+ "/wrap.do?cmd=n19")%>" /></anchor><br/>
<%@ include file="/init/wrap/wrap_head.jsp"%>
<%
	String resultWml = (String)request.getAttribute("resultWml");
	if( resultWml!=null )
	{
	%><%=resultWml%><br/>
<%
	} 
		String path = (String)request.getAttribute("path");
%>
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n1" />
<postfield name="w_type" value="<%=Wrap.CURE %>" />
<postfield name="page_no" value="1" />
</go>药品</anchor>|
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n1" />
<postfield name="w_type" value="<%=Wrap.EQUIP %>" />
<postfield name="page_no" value="1" />
</go>装备</anchor>|
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n1" />
<postfield name="w_type" value="<%=Wrap.BOOK %>" />
<postfield name="page_no" value="1" />
</go>仙书</anchor>|
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n1" />
<postfield name="w_type" value="<%=Wrap.REST %>" />
<postfield name="page_no" value="1" />
</go>材料</anchor>|
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n1" />
<postfield name="w_type" value="<%=Wrap.SHOP %>" />
<postfield name="page_no" value="1" />
</go>商城</anchor>
<br/>