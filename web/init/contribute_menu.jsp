<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%@page import="com.ls.pub.constant.*"%>
<%
	String w_type = request.getParameter("w_type");
	String resultWml = (String)request.getAttribute("resultWml");
	if( resultWml!=null )
	{
	%><%=resultWml%><br />
<%
	} 
	%>
<%
		String path = (String)request.getAttribute("path");
	 %>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n3" />
<postfield name="w_type" value="<%=Wrap.BOOK %>" />
</go>
书卷|
</anchor>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n3" />
<postfield name="w_type" value="<%=Wrap.CURE %>" />
</go>
药品|
</anchor>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n3" />
<postfield name="w_type" value="<%=Wrap.EQUIP %>" />
</go>
装备|
</anchor>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n3" />
<postfield name="w_type" value="<%=Wrap.REST %>" />
</go>
其他|
</anchor>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+path) %>">
<postfield name="cmd" value="n3" />
<postfield name="w_type" value="<%=Wrap.SHOP %>" />
</go>
商城
</anchor>