<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" 	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String uPk = (String) request.getAttribute("uPk");  
		String lid = (String) request.getAttribute("lid");  
		if(uPk == null){
		   uPk = request.getParameter("uPk");
		}
	%>
	请选择分区:<br/> 
	<br/>
	<anchor>
	<go method="post"   href="<%=GameConfig.getContextPath()%>/loginpage.do">
	<postfield name="cmd" value="n2" /> 
	 <postfield name="uPk" value="<%=uPk %>" />
	 <postfield name="lid" value="<%=lid %>" />
	 </go>
	屠龙区（火热）
	</anchor><br/>
	<br/> 
	<anchor>
	 <go href="<%=GameConfig.getContextPath() %>/tomlogin.do" method="post">
	 <postfield name="cmd" value="n2" /> 
	  <postfield name="lid" value="<%=lid %>" />
	 </go>
	返回上一页
	</anchor>
</p>
</card>
</wml>
