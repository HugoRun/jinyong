<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<%
	String menu_id = (String)request.getAttribute("menu_id");
	String hint = (String)request.getAttribute("hint");
	String w_type = (String)request.getAttribute("w_type");
	String page_no = (String)request.getSession().getAttribute("page_no");
	if( hint!=null )
	{
 
%> 
	<%=hint%>
 
<%
	}
	else
	{
		out.print("空指针");
	}
 %> 
 <br/>
<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>" >
	<postfield name="cmd" value="n1" />
	<postfield name="w_type" value="<%=w_type %>" />
	<postfield name="page_no" value="<%=page_no %>" />
	</go>
	返回 
	</anchor>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>