<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%
	String str_Word = (String) request.getAttribute("str_Word");
	String str_Word_2 = (String) request.getAttribute("str_Word_2");
	String str_Word_3 = (String) request.getAttribute("str_Word_3");
%>
<anchor>
<%
	if (str_Word.equals("-1")) {
%>
<go
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/laba.do?cmd=n1")%>"
	method="post">
幸运点点看
<postfield name="num" value="1" />
</go>
<%
	} else {
%>
<%=str_Word%>
<%
	}
%>
</anchor>


<br />
<anchor>
<%
	if (str_Word_2.equals("-1")) {
%>
<go
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/laba.do?cmd=n1")%>"
	method="post">
幸运点点看
<postfield name="num" value="2" />
</go>
<%
	} else {
%>
<%=str_Word_2%>
<%
	}
%>
</anchor>
<br />
<anchor>
<%
	if (str_Word_3.equals("-1")) {
%>
<go
	href="<%=response.encodeURL(GameConfig.getContextPath()
								+ "/laba.do?cmd=n1")%>"
	method="post">
幸运点点看
<postfield name="num" value="3" />
</go>
<%
	} else {
%>
<%=str_Word_3%>
<%
	}
%>
</anchor>

