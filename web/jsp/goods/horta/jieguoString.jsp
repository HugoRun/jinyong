<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.util.StringUtil,java.util.*,com.ls.pub.util.*,com.pm.vo.horta.HortaVO"%>

<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>

<%
	String hint = (String)request.getAttribute("jieguoString");
	String main_type = (String)request.getAttribute("main_type");
%>

<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%	
	if ( hint != null && !hint.equals("") && !hint.equals("null")) {
		out.println(hint+"<br/>");
	}
	 %>	
	 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/horta.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="main_type" value="<%=main_type %>" />
	</go>
		返回上级
	</anchor>
	<br/>
<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
