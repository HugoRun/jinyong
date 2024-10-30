<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.dao.wrapinfo.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ben.*"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
	String propInfoWml = (String)request.getAttribute("propInfoWml");
	String sPk = (String)request.getAttribute("sPk");
	%> 
	<%=propInfoWml %><br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jiaoyi.do")%>">
	<postfield name="cmd" value="n15" />
	<postfield name="sPk" value="<%=sPk %>" />
	</go>
	返回
	</anchor>
</p>
</card>
</wml>
