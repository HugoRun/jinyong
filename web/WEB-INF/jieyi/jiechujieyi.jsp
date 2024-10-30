<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@page import="com.ben.vo.friend.FriendVO"%>
<%@page import="java.util.List"%>
<%@ page pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%
    String pByPk = (String) request.getAttribute("pByPk");
    String pByName = (String) request.getAttribute("pByName");
%>
您确认与<%=pByName%>解除结义吗？
<br />
<anchor>
<go method="post"
    href="<%=response.encodeURL(GameConfig.getContextPath()
                            + "/jieyi.do")%>">
<postfield name="pByPk" value="<%=pByPk%>" />
<postfield name="pByName" value="<%=pByName%>" />
<postfield name="cmd" value="n11" />
</go>
确认
</anchor>
<anchor>
<go
    href="<%=response.encodeURL(GameConfig.getContextPath()
                			+ "/pubbuckaction.do")%>"
	method="get"></go>
取消
</anchor>
<br />
<%@ include file="/WEB-INF/inc/footer.jsp"%>
