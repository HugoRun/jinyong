<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@ include file="/init/wrap/wrap_menu.jsp"%>
<%
		String w_type_a = (String)session.getAttribute("w_type");
		if( w_type_a == null )
		{
			w_type_a = Wrap.EQUIP+"";
		}
		if( Integer.parseInt(w_type_a) == Wrap.EQUIP )
		{
		%><%@ include file="wrapinfo_list_equip.jsp"%><%
		} 
		else
		{
		%><%@ include file="wrapinfo_list_prop.jsp"%><%
		}
%><br/>
<anchor>状态栏<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n1")%>"></go></anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
