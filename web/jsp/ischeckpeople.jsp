<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	String uPk = (String) request.getAttribute("uPk");  
		if(uPk == null){
		   uPk = request.getParameter("uPk");
		}
		String hint = (String) request.getAttribute("hint");
		if (hint != null && !hint.equals("")) {
	%>
	<%=hint%><br/>
	<%
		}
	%>
	<anchor>
	<go href="<%=response.encodeURL(GameConfig.getContextPath()+"/loginpage.do")%>" method="post">
	<postfield name="cmd" value="n1" /> 
	<postfield name="uPk" value="<%=uPk %>" />
	</go>
	返回选区页面
	</anchor><br/>
<%@ include file="/init/return_url/return_zhuanqu.jsp"%>
</p>
</card>
</wml>
