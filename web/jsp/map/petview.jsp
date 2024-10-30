<%@ include file="/init/templete/game_head.jsp"%>
<%@page  pageEncoding="UTF-8"%>
<%
	String petWml = (String)request.getAttribute("petWml");
	String pPks = (String)request.getAttribute("pPks");
	if( petWml!=null )
	{
%> 
<%=petWml%>
<%
	}
	else
	{
		out.print("无<br/>");
	}
%> 
<anchor>
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do") %>">
<postfield name="cmd" value="n16" />
<postfield name="other_p_pk" value="<%=pPks%>" />
</go>
返回
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
