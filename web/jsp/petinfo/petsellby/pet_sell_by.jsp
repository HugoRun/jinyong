<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.dao.info.partinfo.*,com.ben.dao.petinfo.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.*,com.ben.vo.petsell.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="login" title="<bean:message key="gamename"/>">
<p>  
	<%
		
		PetSellVO vo = (PetSellVO)request.getAttribute("vo");
		String ps_pk = (String)request.getAttribute("ps_pk");
		PartInfoDAO dao = new PartInfoDAO(); 
		PetInfoDAO daoss = new PetInfoDAO();
		String pet_name = daoss.pet_name(vo.getPetId());  
	%>
	<%=dao.getPartName(vo.getPPk()+"")%>以<%=vo.getPsSilverMoney() %>两<%=vo.getPsCopperMoney() %>文的价格与您交易
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")%>"> 
	<postfield name="cmd" value="n15" /> 
	<postfield name="pet_pk" value="<%=vo.getPetId() %>" /> 
	<postfield name="ps_pk" value="<%=ps_pk%>" /> 
	</go>
	<%=pet_name%>
	</anchor> 
	<br/>您 
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")%>"> 
	<postfield name="cmd" value="n11" /> 
	<postfield name="ps_pk" value="<%=vo.getPsPk() %>" /> 
	</go>
	是
	</anchor>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/sellinfoaction.do")%>">
	<postfield name="cmd" value="n12" /> 
	<postfield name="ps_pk" value="<%=vo.getPsPk() %>" /> 
	</go>
	否
	</anchor>
	同意接受交易
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
