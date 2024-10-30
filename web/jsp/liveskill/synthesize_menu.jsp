<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p><% 
	int s_type = Integer.parseInt((String)request.getAttribute("s_type"));
	%>
	<%if(s_type == 1){
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	开始烹饪
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="type" value="1" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	学习技能
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="type" value="2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	遗忘技能
	</anchor>
	
	<%
	} 
	%>
	<%if(s_type == 2){
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	开始炼药
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="type" value="1" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	学习技能
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="type" value="2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	遗忘技能
	</anchor>
	
	<%
	} 
	%>
	<%if(s_type == 3){
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	开始锻造
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="type" value="1" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	学习技能
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="type" value="2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	遗忘技能
	</anchor>
	
	<%
	} 
	%>
	<%if(s_type == 4){
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	开始织造
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="type" value="1" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	学习技能
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="type" value="2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	遗忘技能
	</anchor>
	
	<%
	} 
	%>
	<%if(s_type == 5){
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	开始制作
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="type" value="1" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	学习技能
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="type" value="2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	遗忘技能
	</anchor>
	
	<%
	} 
	%>
	<%if(s_type == 6){
	%>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/synthesize.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	开始打造
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="type" value="1" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	学习技能
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/liveskill.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="type" value="2" />
	<postfield name="s_type" value="<%=s_type %>" />
	</go>
	遗忘技能
	</anchor>
	
	<%
	} 
	%>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
