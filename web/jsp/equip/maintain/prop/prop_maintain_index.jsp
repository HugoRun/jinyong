<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%
	String hint = (String)request.getAttribute("hint");
	if( hint!=null )
	{
%>${hint }<br/>
<anchor>确定
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
<postfield name="cmd" value="propMaintain" />
<postfield name="pg_pk" value="${pg_pk }" />
<postfield name="w_type" value="${w_type }" />
</go>
</anchor>
<%
	}
	else
	{
%>
你身上的装备无耐久损耗,不需要修理！
<%
	}
%>
<%@ include file="/init/templete/return_wrap_foot.jsp"%>
