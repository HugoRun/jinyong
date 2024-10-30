<%@ include file="/WEB-INF/inc/header.jsp"%>
<%@ page pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
    <%
		String pet_pk = (String) request.getAttribute("pet_pk");
		String pg_pk = (String)request.getAttribute("pg_pk");
		String resultWml = (String) request.getAttribute("resultWml");
	%> 
	<%
		if( resultWml!=null )
		{
			%><%=resultWml%><%
		}
		else
		{
			out.print("宠物信息错误<br/>");
		}
	 %>
<%@ include file="/WEB-INF/rank/beInclude.jsp"%>
<anchor><go href="<%=response.encodeURL(GameConfig.getContextPath()+"/pubbuckaction.do")%>" method="get"></go>返回游戏</anchor><br/>
<%@ include file="/WEB-INF/inc/footer.jsp"%>
