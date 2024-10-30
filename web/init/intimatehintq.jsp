<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page
    import="com.ls.pub.config.GameConfig"%>
<%
	String intimateHint = (String) request.getAttribute("intimateHint");
	if (intimateHint != null) {
%><%=intimateHint%>
<%
	}
%>