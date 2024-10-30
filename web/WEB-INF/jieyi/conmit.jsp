<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8"%><%@page
	import="com.ls.pub.config.GameConfig"%>
<%
	String pByName = (String) request.getAttribute("pByName");
	String pByPk = (String) request.getAttribute("pByPk");
%>
你确定要与玩家<%=pByName%>金兰结义吗？
<br />
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/jieyi.do")%>">
<postfield name="cmd" value="n12" />
<postfield name="pByPk" value="<%=pByPk%>" />
<postfield name="pByName" value="<%=pByName%>" />
</go>
确定
</anchor>
<br />
<anchor>
<go method="post"
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/jieyi.do")%>">
<postfield name="cmd" value="n7" />
</go>
返回
</anchor>
<br />
<anchor>
<go
	href="<%=response.encodeURL(GameConfig.getContextPath()
							+ "/pubbuckaction.do")%>"
	method="get"></go>
返回游戏
</anchor>
<br />




<%@ include file="/WEB-INF/inc/footer.jsp"%>
