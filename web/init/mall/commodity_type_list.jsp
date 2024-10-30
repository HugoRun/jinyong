<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
商城道具:
<br />
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/emporiumaction.do?cmd=n9")%>">
<postfield name="typeid" value="4" />
</go>
道具类
</anchor>
<br />