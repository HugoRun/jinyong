<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@ include file="storage_menu.jsp"%>
<%@page import="com.ls.pub.constant.Wrap" %> 
<%
		String w_type_a = (String)request.getAttribute("w_type");
		if( w_type_a == null )
		{
			w_type_a = "2";
		}
		if( Integer.parseInt(w_type_a) == Wrap.EQUIP )
		{
		%><%@ include file="storage_list_equip.jsp"%><%
		} 
		else
		{
		%><%@ include file="storage_list_prop.jsp"%><%
		}
%>
<%@ include file="/init/templete/game_foot.jsp"%>
