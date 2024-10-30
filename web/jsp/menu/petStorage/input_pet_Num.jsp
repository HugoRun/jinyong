<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ben.vo.petinfo.*"%>
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p> 
<%
	String menu_id = (String)request.getAttribute("menu_id");
	List pet_list = (List)request.getAttribute("pet_list");
	int petStorageNumber = 0;
%> 
宠物列表<br/>
<%
	if( pet_list!=null && pet_list.size()!=0 )
	{
		for( int i=0;i<pet_list.size();i++ )
		{
			PetInfoVO petInfoVO = (PetInfoVO)pet_list.get(i);
			//npcshop = (NpcShopVO)npcshops.get(i);
			
			%>
			<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/addPetStorage.do")%>">
			<postfield name="cmd" value="n3" />
			<postfield name="petPk" value="<%=petInfoVO.getPetPk() %>" />
			</go>
			<%=StringUtil.isoToGBK(petInfoVO.getPetNickname()) %>
			</anchor>|<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/addPetStorage.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="petId" value="<%=petInfoVO.getPetPk() %>" />
			</go>
			储存
			</anchor>
			<br/>
			
			<%
		}
 	}
 	else
 	{
 		%>暂无宠物<% 
 	}
  %>
  <br/>
   <%
  
   String petNumber =  (String) request.getAttribute("petStorageNumber");
   	int petNumbers = 5;%>
   <%if(petNumber == null || petNumber == ""){
   	petNumbers = 5;
   	}else {
   	petNumbers = 5 - Integer.valueOf(petNumber);
    %>
  兽栏:<%=petNumbers %>/5
  <%} %>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>