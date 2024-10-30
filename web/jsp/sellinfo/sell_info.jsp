<%@page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
您正在与${other.name}进行物品交易!<br/>
钱财:${me.basicInfo.copper}<%=GameConfig.getMoneyUnitName() %><br/>
<%@ include file="/init/templete/hint_message.jsp"%>
	<%
	String c = (String)request.getAttribute("c");
	if(c!=null){
	%>
您身上没有那么多钱<br/>
	<%} %>
<%@ include file="sell_info_menu.jsp"%>
	<% 
		String w_type_a = (String)request.getAttribute("w_type");
		if( w_type_a == null )
		{
			w_type_a = "3";
		}
		if( Integer.parseInt(w_type_a) == Wrap.EQUIP )
		{
		%><%@ include file="sell_info_equip_list.jsp"%>
		<%
		} 
		else
		{
		%><%@ include file="sell_info_prop_list.jsp"%><%
		}
	%> 
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
<postfield name="cmd" value="n1" />
<postfield name="pByPk" value="${pByPk }" /> 
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>