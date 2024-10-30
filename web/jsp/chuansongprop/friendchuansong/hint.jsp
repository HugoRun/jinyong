<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.vo.friend.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	<%
		String display = (String)request.getAttribute("display");
		String w_type = (String) request.getAttribute("w_type");
		String pg_pk = (String) request.getAttribute("pg_pk");
		String prop_id = (String) request.getAttribute("goods_id");
		String goods_type = (String) request.getAttribute("goods_type");
	%>
	<%=display %><br/>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/friendscs.do") %>"> 
	<postfield name="cmd" value="n1" />
	<postfield name="w_type" value="<%=w_type %>" />
	<postfield name="goods_type" value="<%=goods_type %>" />
	<postfield name="pg_pk" value="<%=pg_pk %>" />
	<postfield name="goods_id" value="<%=prop_id %>" />
	</go>
	返回
	</anchor> 
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
