<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*,com.ben.dao.petinfo.*,com.ben.dao.info.partinfo.*"
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
		String hint = (String)request.getAttribute("hint"); 
		if(hint != null){
		out.println(hint);
		}else{
		PetInfoDAO dao = new PetInfoDAO();
		String pet_name = dao.pet_name(Integer.parseInt(petPk));
		PartInfoDAO daoq = new PartInfoDAO(); 
	%>  
	您确定要将宠物
	<%=pet_name%>
	交易给
	<%=daoq.getPartName(pByPk)%>
	吗？
	<br/>
	请输入要交易的宠物金额:
	<br/>
	<input name="pSilver"   type="text" format="*N"  size="10" />两
	<br/>
	<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/swapaction.do")%>">
	<postfield name="cmd" value="n12" />
	<postfield name="pByPk" value="<%=pByPk %>" />  
	<postfield name="pSilver" value="$pSilver" /> 
	<postfield name="petPk" value="<%=petPk %>" /> 
	</go>
	确定交易
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
	<%} %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
