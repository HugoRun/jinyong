<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%	
		String position = (String)request.getAttribute("position");
		String equip_display = (String)request.getAttribute("equip_display");
		String pageNo = (String)request.getAttribute("pageNo");
%>
<%=equip_display %><br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=position%>" />
	</go>
	返回
	</anchor> 
<%@ include file="/init/templete/game_foot.jsp"%>