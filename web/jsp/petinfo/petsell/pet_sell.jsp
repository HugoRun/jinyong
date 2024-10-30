<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.vo.petinfo.PetInfoVO,com.ben.dao.info.partinfo.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%  
		String pByPk = (String)request.getAttribute("pByPk");//被请求人的ID
		String pByuPk = (String)request.getAttribute("pByuPk");//被请求人的ID
		List list = (List) request.getAttribute("list");//被请求人的ID 
		PartInfoDAO partInfoDAO = new PartInfoDAO();  
	%>
	您正在与 <%=partInfoDAO.getPartName(pByPk) %>进行宠物交易！ 
	<br/>
	请选择您要交易的宠物:
	<br/>
	<%   
	    if(list != null && list.size()!=0){
		for(int i = 0 ; i < list.size(); i++){
		PetInfoVO vo = (PetInfoVO)list.get(i);	 
	%>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">  
	<postfield name="cmd" value="n10" />
	<postfield name="pByPk" value="<%=pByPk %>" />  
	<postfield name="petPk" value="<%=vo.getPetPk() %>" /> 
	<postfield name="petId" value="<%=vo.getPetId()  %>" /> 
	</go>
	<%=vo.getPetName() %>
	</anchor> 
	<br/>
	<%}}else{ %>暂无宠物<br/><%} %>
	<anchor> 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="pByuPk" value="<%=pByuPk%>" />
	<postfield name="pByPk" value="<%=pByPk %>" /> 
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
