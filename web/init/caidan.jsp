<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/communioninfoaction.do?cmd=n1")%>">
<postfield name="Type" value="1" />
</go>
公
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/communioninfoaction.do?cmd=n2")%>">
<postfield name="Type" value="2" />
</go>
种
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/communioninfoaction.do?cmd=n4")%>">
<postfield name="Type" value="4" />
</go>
氏
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/communioninfoaction.do?cmd=n3")%>">
<postfield name="Type" value="3" />
</go>
队
</anchor>
|
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/communioninfoaction.do?cmd=n5")%>">
<postfield name="Type" value="5" />
</go>
密
</anchor>
|
<anchor>
<go method="get"
	href="<%=response.encodeURL(GameConfig.getContextPath()+"/systemcommaction.do?cmd=n1")%>" ></go>
系
</anchor>
