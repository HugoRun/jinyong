<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.util.DateUtil"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig"%>
<br />
<anchor>
<go method="get"
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/updecompose.do?cmd=n8")%>" ></go>
返回游戏
</anchor>
<br />
----------------------
<br />
报时:<%=DateUtil.getMainPageCurTimeStr()%>
