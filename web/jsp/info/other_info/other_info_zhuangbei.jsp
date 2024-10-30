<%@ page pageEncoding="UTF-8"%>
<%@ include file="/init/templete/game_head.jsp"%>

<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.model.equip.PositionOnEquip"%>
<%@page import="com.ls.ben.vo.info.pet.PetInfoVO"%>
<%
		String pPks = (String)request.getAttribute("pPks");
	    String backtype = (String)request.getSession().getAttribute("backtype");
	    PlayerEquipVO partEquipVO1 = (PlayerEquipVO)request.getAttribute("partEquipVO1");
	    PlayerEquipVO partEquipVO2 = (PlayerEquipVO)request.getAttribute("partEquipVO2");
	    PlayerEquipVO partEquipVO3 = (PlayerEquipVO)request.getAttribute("partEquipVO3");
	    PlayerEquipVO partEquipVO4 = (PlayerEquipVO)request.getAttribute("partEquipVO4");
	    PlayerEquipVO partEquipVO5 = (PlayerEquipVO)request.getAttribute("partEquipVO5");
	    PlayerEquipVO partEquipVO6 = (PlayerEquipVO)request.getAttribute("partEquipVO6");
	    PlayerEquipVO partEquipVO7 = (PlayerEquipVO)request.getAttribute("partEquipVO7");
	    PlayerEquipVO partEquipVO8 = (PlayerEquipVO)request.getAttribute("partEquipVO8"); 
	    PetInfoVO petInfoVO = (PetInfoVO)request.getAttribute("petInfoVO");
%> 
	武器:
	<%if(partEquipVO1 != null){ 
	%>  
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n17" /> 
	<postfield name="pwPk" value="<%=partEquipVO1.getPwPk()%>" /> 
	 <postfield name="pPks" value="<%=pPks%>" /> 
	</go>
	<%=partEquipVO1.getWName() %>
	</anchor>
	<%}else { %>
	无
	<%} %>
	<br/>
	头部: 
	<%if(partEquipVO2 != null){ 
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n17" /> 
	<postfield name="pwPk" value="<%=partEquipVO2.getPwPk()%>" /> 
	 <postfield name="pPks" value="<%=pPks%>" /> 
	</go>
	<%=partEquipVO2.getWName() %>
	</anchor>
	<%}else { %>
	无
     <%}%>
	<br/> 
	身体:
	<%if(partEquipVO3 != null){ 
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n17" />
	<postfield name="pwPk" value="<%=partEquipVO3.getPwPk()%>" /> 
	 <postfield name="pPks" value="<%=pPks%>" /> 
	</go>
	<%=partEquipVO3.getWName() %>
	</anchor>
	<%}else { %>
	无
	<%} %>
	<br/>
	腿部:
	<%if(partEquipVO4 != null){ 
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n17" />
	<postfield name="pwPk" value="<%=partEquipVO4.getPwPk()%>" /> 
	 <postfield name="pPks" value="<%=pPks%>" /> 
	</go>
	<%=partEquipVO4.getWName() %>
	</anchor> 
	<%}else { %>
	无
	<%} %>
	<br/>
	脚部:
	<%if(partEquipVO5 != null){ 
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n17" />
	<postfield name="pwPk" value="<%=partEquipVO5.getPwPk()%>" /> 
	 <postfield name="pPks" value="<%=pPks%>" /> 
	</go>
	<%=partEquipVO5.getWName() %>
	</anchor> 
	<%}else { %>
	无
	<%} %>
	<br/> 
	法宝1:
	<%if(partEquipVO6 != null){ 
	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n17" />
	<postfield name="pwPk" value="<%=partEquipVO6.getPwPk()%>" />
	 <postfield name="pPks" value="<%=pPks%>" /> 
	</go>
	<%=partEquipVO6.getWName() %>
	</anchor> 
	<%}else { %>
	无
	<%}%>
	<br/>
	法宝2:
	<%if(partEquipVO7 != null){ 
	 %>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n17" />
	<postfield name="pwPk" value="<%=partEquipVO7.getPwPk()%>" /> 
	 <postfield name="pPks" value="<%=pPks%>" /> 
	</go>
	<%=partEquipVO7.getWName() %>
	</anchor> 
	<%}else { %>
	无
	<%} %>
	<br/>
	法宝3:
	<%if(partEquipVO8 != null){%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n17" />
	<postfield name="pwPk" value="<%=partEquipVO8.getPwPk()%>" /> 
	 <postfield name="pPks" value="<%=pPks%>" /> 
	</go>
	<%=partEquipVO8.getWName() %>
	</anchor>
	<%}else { %>无<%} %>
	<br/>
${suitEffectDescribte }
${wxTypeAttribute }
	 <%if (backtype !=null && backtype.contains("n")){%>
	 <anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/untangle.do?cmd=n3")%>">
	 <postfield name="pPk" value="<%=pPks%>" />
	 <postfield name="number" value="<%=backtype%>" />
	 </go>
	 返回
	 </anchor>
	 <%}else{%>
	 <anchor> <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
	 <postfield name="cmd" value="n13" />   
	 <postfield name="pPks" value="<%=pPks%>" /> 
	 </go>返回</anchor>
	 <%} %>
<%@ include file="/init/templete/game_foot.jsp"%>
