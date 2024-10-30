<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>

	<%
	String hint = (String)request.getAttribute("hint");
	String pwPk = (String)request.getAttribute("pwPk");
	String wName = (String)request.getAttribute("wName");
	String tableType = (String)request.getAttribute("tableType");
	String goodsType = (String)request.getAttribute("goodsType");
	String bangding = (String)request.getAttribute("bangding");
	String types = (String)request.getAttribute("types");
	String wId = (String)request.getAttribute("wId");
	if(types != null && types.equals("1")){
	%>
	<%=hint%>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="pwPk" value="<%=pwPk %>" />
	<postfield name="wId" value="<%=wId %>" />
	<postfield name="wName" value="<%=wName %>" />
	<postfield name="tableType" value="<%=tableType %>" />
	<postfield name="goodsType" value="<%=goodsType %>" />
	<postfield name="bangding" value="<%=bangding %>" />
	<postfield name="type" value="<%=types %>" />
	</go>
	确定
	</anchor> 
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="w_type" value="3" />
	</go>
	返回
	</anchor>
	<%
	}else{
	%>
	<%=hint%> 
	<br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>">
	<postfield name="cmd" value="n1" />
	<postfield name="w_type" value="3" />
	</go>
	返回
	</anchor>
	<%}	 %>
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
