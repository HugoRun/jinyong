<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%>
<%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.info.partinfo.PlayerEquipVO"%>
<%@page import="com.ls.model.equip.PositionOnEquip"%>
<%@page import="com.ls.ben.vo.goods.prop.PropVO"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="equip" title="<s:message key = "gamename"/>">
<p>
<%@ include file="part_info_menu_head.jsp"%>
<%   
	    String hint = (String)request.getAttribute("hint");
	    PlayerEquipVO partEquipVO1 = (PlayerEquipVO)request.getAttribute("partEquipVO1");
	    PlayerEquipVO partEquipVO2 = (PlayerEquipVO)request.getAttribute("partEquipVO2");
	    PlayerEquipVO partEquipVO3 = (PlayerEquipVO)request.getAttribute("partEquipVO3");
	    PlayerEquipVO partEquipVO4 = (PlayerEquipVO)request.getAttribute("partEquipVO4");
	    PlayerEquipVO partEquipVO5 = (PlayerEquipVO)request.getAttribute("partEquipVO5");
	    PlayerEquipVO partEquipVO6 = (PlayerEquipVO)request.getAttribute("partEquipVO6");
	    PlayerEquipVO partEquipVO7 = (PlayerEquipVO)request.getAttribute("partEquipVO7");
	    PlayerEquipVO partEquipVO8 = (PlayerEquipVO)request.getAttribute("partEquipVO8"); 
	    PropVO vo = (PropVO)request.getAttribute("propVO"); 
	    String suitEffectDescribte = (String)request.getAttribute("suitEffectDescribte");
	    String wxTypeAttribute = (String)request.getAttribute("wxTypeAttribute");
	    if(hint!=null){
	    %><%=hint %><br/><% 
	    }
%> 
	武器:
	<%if(partEquipVO1 != null){ 
	%>  
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n4")%>"> 
	<postfield name="pwPk" value="<%=partEquipVO1.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.WEAPON %>" />
	</go>
	<%=partEquipVO1.getFullName() %>
	</anchor>
	 <anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n13")%>"> 
	<postfield name="pwPk" value="<%=partEquipVO1.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.WEAPON %>" />
	</go>
	卸下
	</anchor>
	<%}else { %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=PositionOnEquip.WEAPON %>" />
	</go>
	无
	</anchor>
	<%} %>
	<br/>
	头部: 
	<%if(partEquipVO2 != null){ 
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n4" /> 
	<postfield name="pwPk" value="<%=partEquipVO2.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.HAT %>" />
	</go>
	<%=partEquipVO2.getFullName() %>
	</anchor>
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n13" /> 
	<postfield name="pwPk" value="<%=partEquipVO2.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.HAT %>" />
	</go>
	卸下
	</anchor>
	<%}else { %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=PositionOnEquip.HAT %>" />
	</go>
	无
	</anchor>
     <%}%>
	<br/> 
	身体:
	<%if(partEquipVO3 != null){ 
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n4" />
	<postfield name="pwPk" value="<%=partEquipVO3.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.CLOTHING %>" />
	</go>
	<%=partEquipVO3.getFullName() %>
	</anchor>
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n13" /> 
	<postfield name="pwPk" value="<%=partEquipVO3.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.CLOTHING %>" />
	</go>
	卸下
	</anchor>
	<%}else { %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=PositionOnEquip.CLOTHING %>" />
	</go>
	无
	</anchor>
	<%} %>
	<br/>
	腿部:
	<%if(partEquipVO4 != null){ 
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n4" />
	<postfield name="pwPk" value="<%=partEquipVO4.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.TROUSERS %>" />
	</go>
	<%=partEquipVO4.getFullName() %>
	</anchor> 
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n13" /> 
	<postfield name="pwPk" value="<%=partEquipVO4.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.TROUSERS %>" />
	</go>
	卸下
	</anchor>
	<%}else { %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=PositionOnEquip.TROUSERS %>" />
	</go>
	无
	</anchor>
	<%} %>
	<br/>
	脚部:
	<%if(partEquipVO5 != null){ 
	%>
	<anchor>
	<go method="post"  href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n4" />
	<postfield name="pwPk" value="<%=partEquipVO5.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.SHOES %>" />
	</go>
	<%=partEquipVO5.getFullName() %>
	</anchor> 
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n13" /> 
	<postfield name="pwPk" value="<%=partEquipVO5.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.SHOES %>" />
	</go>
	卸下
	</anchor>
	<%}else { %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=PositionOnEquip.SHOES %>" />
	</go>
	无
	</anchor>
	<%} %>
	<br/> 
	法宝:
	<%if(partEquipVO6 != null){ 
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n4" />
	<postfield name="pwPk" value="<%=partEquipVO6.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.JEWELRY_1 %>" />
	</go>
	<%=partEquipVO6.getFullName() %>
	</anchor> 
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n13" /> 
	<postfield name="pwPk" value="<%=partEquipVO6.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.JEWELRY_1 %>" />
	</go>
	卸下
	</anchor>
	<%}else { %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=PositionOnEquip.JEWELRY_1 %>" />
	</go>
	无
	</anchor>
	<%}%>
	<br/>
	法宝:
	<%if(partEquipVO7 != null){ 
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n4" />
	<postfield name="pwPk" value="<%=partEquipVO7.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.JEWELRY_2 %>" />
	</go>
	<%=partEquipVO7.getFullName() %>
	</anchor> 
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n13" /> 
	<postfield name="pwPk" value="<%=partEquipVO7.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.JEWELRY_2 %>" />
	</go>
	卸下
	</anchor>
	<%}else { %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=PositionOnEquip.JEWELRY_2 %>" />
	</go>
	无
	</anchor>
	<%} %>
	<br/>
	法宝:
	<%if(partEquipVO8 != null){ 
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n4" />
	<postfield name="pwPk" value="<%=partEquipVO8.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.JEWELRY_3 %>" />
	</go>
	<%=partEquipVO8.getFullName() %>
	</anchor> 
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n13" /> 
	<postfield name="pwPk" value="<%=partEquipVO8.getPwPk()%>" /> 
	<postfield name="position" value="<%=PositionOnEquip.JEWELRY_3 %>" />
	</go>
	卸下
	</anchor>
	<%}else { %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n10" />
	<postfield name="position" value="<%=PositionOnEquip.JEWELRY_3 %>" />
	</go>
	无
	</anchor>
	<%} %>
	<br/>
仙灵:
	<%if(vo != null){ 
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n20" />
	<postfield name="prop_id" value="<%=vo.getPropID() %>" />
	<postfield name="wupinlan" value="1" />
	</go>
	<%=vo.getPropName() %>
	</anchor>
	 <anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>"> 
	<postfield name="cmd" value="n19" /> 
	</go>
	卸下
	</anchor>
	<%}else { %>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do")%>">
	<postfield name="cmd" value="n21" />
	<postfield name="wupinlan" value="1" />
	</go>
	无
	</anchor>
	<%} %>
	<br/>
<%=suitEffectDescribte %>
<%=wxTypeAttribute %>
<%@ include file="part_info_menu_foot.jsp"%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
