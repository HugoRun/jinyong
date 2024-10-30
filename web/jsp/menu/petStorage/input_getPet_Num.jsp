<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@page import="com.ls.ben.vo.storage.*"%> 
<%@page import="com.ls.pub.util.*"%>
<%@page import="com.ben.vo.petinfo.*"%>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<%
	String menu_id = (String)request.getAttribute("menu_id");
	List pet_list = (List)request.getAttribute("pet_list");
	WareHouseVO wareHouseVO = null;
	int uwPetNumber = 5;
%> 
宠物列表<br/>
<%--
	if( pet_list!=null && pet_list.size()!=0 )
	{
		for( int i=0;i<pet_list.size();i++ )
		{
		wareHouseVO = (WareHouseVO)pet_list.get(i);
			//npcshop = (NpcShopVO)npcshops.get(i);
			String article = wareHouseVO.getUwArticle();
			if(article != null ||article != ""){
			String[] articles = article.split("-");
			for(int a=0;a<articles.length;a++){
				String petPk = articles[a];
				PetInfoDAO petInfoDAO = new PetInfoDAO();
				PetInfoVO petInfoVO = petInfoDAO.getPetInfoView(petPk);
			%>
			<%=petInfoVO.getPetNickname() %>--------
			//npcshop = (NpcShopVO)npcshops.get(i); --%>
			
			<%
	if( pet_list!=null && pet_list.size()!=0 )
	{
		for( int i=0;i<pet_list.size();i++ )
		{
			PetInfoVO petInfoVO = (PetInfoVO)pet_list.get(i);
			//npcshop = (NpcShopVO)npcshops.get(i);
			%>
			<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getPetStorage.do")%>">
			<postfield name="cmd" value="n3" />
			<postfield name="petPk" value="<%=petInfoVO.getPetPk() %>" />
			</go>
			<%=StringUtil.isoToGBK(petInfoVO.getPetNickname()) %>
			</anchor>|<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/menu/getPetStorage.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="petId" value="<%=petInfoVO.getPetPk() %>" />
			</go>
			取出
			</anchor>
			<br/>
		
			<%
		}}
 	else
 	{
 		%>暂无宠物<% 
 	}
  %>
<br/>
  <%if( pet_list!=null && pet_list.size()!=0 ){
  PetInfoVO petInfoVO = (PetInfoVO)pet_list.get(0);
	int petNumber = 5 - pet_list.size();%>
  兽栏:<%=petNumber %>/5
  <%}else { %>
  兽栏:5/5
  <%} %>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>