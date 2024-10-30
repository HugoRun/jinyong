<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@page import="com.ls.pub.constant.Wrap"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%
		String w_type = (String)request.getAttribute("w_type");
		PlayerEquipVO equip = (PlayerEquipVO) request.getAttribute("equip");
		String equip_display = (String)request.getAttribute("equip_display");
		
		if( equip_display != null && !equip_display.equals("")) {
			out.print(equip_display);
		}else { %>
无
<%} %>
<anchor>储存
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do")%>">
<postfield name="cmd" value="n2" />
<postfield name="w_type" value="${w_type}" />
<postfield name="equip_id" value="<%=equip.getPwPk() %>" />
<postfield name="pageNo" value="${pageNo }" />
</go>
</anchor><br/>
<anchor>返回
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/storage.do") %>">
<postfield name="cmd" value="n1" />
<postfield name="w_type" value="${w_type }" />
<postfield name="pageNo" value="${pageNo }" />
</go>
</anchor>
<%@ include file="/init/templete/game_foot.jsp"%>
