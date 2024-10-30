<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%
		String pwPk = (String)request.getAttribute("pwPk");
		String position = (String)request.getAttribute("position");
		String equip_display = (String)request.getAttribute("equip_display");
		
		if(equip_display != null && !equip_display.equals("") ) {
			out.print(equip_display);
		}else { %>
			无
<%} %>	
<br/>
	
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=position%>" />
	</go>
	更换
	</anchor>  
	<br/>
	
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equiprelela.do")%>"> 
	<postfield name="cmd" value="n1" />
	<postfield name="pwPk" value="<%=pwPk %>" />
	<postfield name="return_type" value="body" />
	</go>
	 展示
	</anchor>
	<br/>
<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n3")%>" />返回</anchor> 
<%@ include file="/init/templete/game_foot.jsp"%>