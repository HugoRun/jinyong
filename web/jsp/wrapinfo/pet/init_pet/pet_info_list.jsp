<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ls.ben.vo.info.pet.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.util.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%  
		String pg_pk = (String)request.getAttribute("pg_pk");
	   	List<PetInfoVO> pets = (List)request.getAttribute("pets");
	   	String page_no = (String)request.getSession().getAttribute("page_no");
	   	if( pets!=null && pets.size()>0 )
	   	{
	%>  
	可以重生宠物列表<br/>
	<%
		for(PetInfoVO pet:pets)
		{
	 %>
    <anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap/pet.do")%>"> 
	<postfield name="cmd" value="n2" /> 
	<postfield name="pet_pk" value="<%=pet.getPetPk() %>" /> 
	<postfield name="pg_pk" value="<%=pg_pk %>" /> 
	</go>
	<%=StringUtil.isoToGB(pet.getPetNickname())%>
	</anchor>
	<br/>
	<%
		}
	}
	else
	{
		%>
			您还没有可以重生的宠物<br/>
		<%
	}
	%>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n1" />  
	<postfield name="w_type" value="6" /> 
	<postfield name="page_no" value="<%=page_no %>" /> 
	</go>
	返回
	</anchor>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
