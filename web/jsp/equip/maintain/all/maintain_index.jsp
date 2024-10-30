<%@ include file="/init/templete/game_head.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<%
	String hint = (String)request.getAttribute("hint");
 %>
${hint }
修理全身装备将花费${feeDes }!0耐久装备不能修理!<br/>
<%if(hint==null){ %>
<anchor>确定修理
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/equip.do")%>"> 
<postfield name="cmd" value="maintainAll" />
</go>
</anchor>
<%}else{
%>确定修理<%
} %>
<%@ include file="/init/templete/game_foot.jsp"%>
