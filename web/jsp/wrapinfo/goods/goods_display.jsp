<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
	<% Object exmess=request.getAttribute("exmess");
	   if(exmess!=null){
	   %>
	   <%=exmess.toString()%><br/>
	   <%
	   }
	%>
	
	<%
		String goods_display = (String) request.getAttribute("goods_display");
		String goods_type = (String) request.getAttribute("goods_type");
		String wupinlan = (String) request.getAttribute("wupinlan");
		if (goods_display != null) {
	%>
	<%=goods_display%>
	<%
		} else {
			out.print("空指针");
		}
		if(wupinlan!=null&&wupinlan.equals("1")){
	%> 
	<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/stateaction.do?cmd=n3")%>"></go>返回</anchor>
	<%}else {%>
	<anchor>返回 
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/wrap.do")%>" >
	<postfield name="cmd" value="n1" />
	</go>
	</anchor>
	<% } %>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
