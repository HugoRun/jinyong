<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%
		PlayerEquipVO partEquipVO = (PlayerEquipVO) request.getAttribute("equip");
		String pByPk = (String)request.getAttribute("pByPk");
		
		String equip_display = (String)request.getAttribute("equip_display");
		
		if(equip_display != null && !equip_display.equals("") ) {
			out.print(equip_display);
		}else { %>
			无
<%} %>	
<anchor> 确定
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>"> 
<postfield name="cmd" value="n8" />
<postfield name="wName" value="<%=partEquipVO.getWName() %>" />
<postfield name="pwPk" value="<%=partEquipVO.getPwPk() %>" />
<postfield name="pByPk" value="<%=pByPk %>" />
<postfield name="wProtect" value="<%=partEquipVO.getWProtect() %>" />
<postfield name="bangding" value="<%=partEquipVO.getWBonding() %>" />
</go>
</anchor>  
<br/> 
<anchor> 返回
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>"> 
<postfield name="cmd" value="n3" />
<postfield name="pByPk" value="<%=pByPk %>" /> 
<postfield name="w_type" value="3" /> 
</go>
</anchor>  
<%@ include file="/init/templete/game_foot.jsp"%>