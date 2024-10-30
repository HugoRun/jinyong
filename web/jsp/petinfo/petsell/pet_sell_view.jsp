<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.petinfo.*,com.ben.vo.petinfo.PetInfoVO,com.ben.dao.info.partinfo.*,com.ben.vo.info.partinfo.PartInfoVO"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<% 
		String pByPk = (String)request.getAttribute("pByPk");//被请求人的ID
		String pByuPk = (String)request.getAttribute("pByuPk");//被请求人的ID
		String petPk = (String)request.getAttribute("petPk");
		String petId = (String)request.getAttribute("petId");//被请求人的ID
		String resultWml = (String)request.getAttribute("resultWml");//被请求人的ID 
		if(resultWml!=null){
	 %> 
	 <%=resultWml %>
	 <%} %> 
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>"> 
	<postfield name="cmd" value="n11" />
	<postfield name="pByPk" value="<%=pByPk %>" />   
	<postfield name="petPk" value="<%=petPk  %>" /> 
	</go>
	确定
	</anchor>
	<br />
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
	<postfield name="cmd" value="n9" />
	<postfield name="pByuPk" value="<%=pByuPk %>" />
	<postfield name="pByPk" value="<%=pByPk %>" /> 
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
