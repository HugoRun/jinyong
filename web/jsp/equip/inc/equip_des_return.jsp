<%@ page pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig"%>
<%
	String pre = (String)request.getAttribute("pre");
	if( pre!=null )
	{
		if( pre.equals("product") )
		{
	%>
	<anchor>返回
	<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=n1")%>" method="post" >
	<postfield name="page_no" value="${page_no }"/>
	</go>
	</anchor>
	<%
		}
		else if( pre.equals("punch"))
		{
	%>
	<anchor>返回
	<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=punchEquipList")%>" method="post" >
	<postfield name="page_no" value="${page_no }"/>
	</go>
	</anchor>
	<%
		}
		else if( pre.equals("inlay"))
		{
	%>
	<anchor>返回
	<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd=inlayEquipList")%>" method="post" >
	<postfield name="page_no" value="${page_no }"/>
	</go>
	</anchor>
	<%
		}
		else if( pre.equals("bad") || pre.equals("bind") ||  pre.equals("quality") || pre.equals("protect") )
		{
	%>
	<anchor>返回
	<go href="<%=response.encodeURL(GameConfig.getContextPath()+ "/equip.do?cmd="+pre+"EquipList")%>" method="post" >
	<postfield name="page_no" value="${page_no }"/>
	<postfield name="pg_pk" value="${pg_pk }"/>
	<postfield name="w_type" value="${w_type }"/>
	</go>
	</anchor>
	<%
		}
	}
%>