<%@page pageEncoding="UTF-8"%>
<%@include file="/init/templete/game_head.jsp" %>
您正准备赠送${other.name }<%=GameConfig.getMoneyUnitName() %>!<br/>
钱财:${me.basicInfo.copper}<%=GameConfig.getMoneyUnitName() %><br/>
<%@include file="/init/templete/hint_message.jsp" %>
给予钱财:<br/>
	<%
	String c = (String)request.getAttribute("c");
	if(c!=null){
	%>
	 您身上没有那么多钱<br/>
	<%} %>
<input name="pSilver" type="text" format="*N"  maxlength="8" /><%=GameConfig.getMoneyUnitName() %><br/>
<anchor>确定 
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
<postfield name="cmd" value="n1" />
<postfield name="pByPk" value="${pByPk }" /> 
<postfield name="pSilver" value="$(pSilver)" /> 
</go>
</anchor> 
<br/>
<anchor> 返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
<postfield name="cmd" value="n1" />
<postfield name="pByPk" value="${pByPk }" /> 
</go>
</anchor>
<%@include file="/init/templete/game_foot.jsp" %>
