<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.constant.Shortcut"%>
<%@page import="java.util.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %><%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
<%
	String aPpk = (String) request.getAttribute("aPpk");
	String bPpk = (String) request.getAttribute("bPpk");
	String sc_pk = (String) request.getAttribute("sc_pk");
	String pk = (String) request.getAttribute("pk");
	String shortType = (String) request.getAttribute("shortType");	// 用来确定究竟是普通情况设置快捷键还是战斗情况下设置快捷键
%>
	请选择该快捷键设置的种类:
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
	<postfield name="cmd" value="n5" />
	<postfield name="aPpk" value="<%=aPpk %>" />
	<postfield name="bPpk" value="<%=bPpk %>" />
	<postfield name="shortType" value="<%=shortType %>" />
	<postfield name="sc_pk" value="<%=sc_pk%>" />
	<postfield name="pk" value="<%=pk %>" />
	</go>
	技能
	</anchor>
	<br/>

	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
	<postfield name="cmd" value="n6" />
	<postfield name="aPpk" value="<%=aPpk %>" />
	<postfield name="bPpk" value="<%=bPpk %>" />
	<postfield name="shortType" value="<%=shortType %>" />
	<postfield name="sc_pk" value="<%=sc_pk%>" />
	<postfield name="type" value="<%=Shortcut.CURE%>" />
	<postfield name="pk" value="<%=pk %>" />
	</go>
	药品
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
	<postfield name="cmd" value="prop" />
	<postfield name="aPpk" value="<%=aPpk %>" />
	<postfield name="bPpk" value="<%=bPpk %>" />
	<postfield name="shortType" value="<%=shortType %>" />
	<postfield name="sc_pk" value="<%=sc_pk%>" />
	<postfield name="type" value="<%=Shortcut.ATTACKPROP%>" />
	<postfield name="pk" value="<%=pk %>" />
	</go>
	道具
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="aPpk" value="<%=aPpk %>" />
	<postfield name="bPpk" value="<%=bPpk %>" />
	<postfield name="shortType" value="<%=shortType %>" />
	<postfield name="sc_pk" value="<%=sc_pk%>" />
	<postfield name="type" value="<%=Shortcut.LOOKINFO%>" />
	<postfield name="pk" value="<%=pk %>" />
	</go>
	查看
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="aPpk" value="<%=aPpk %>" />
	<postfield name="bPpk" value="<%=bPpk %>" />
	<postfield name="sc_pk" value="<%=sc_pk%>" />
	<postfield name="type" value="<%=Shortcut.ATTACK%>" />
	<postfield name="pk" value="<%=pk %>" />
	<postfield name="shortType" value="<%=shortType %>" />
	</go>
	攻击
	</anchor>
	<br/>
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
	<postfield name="cmd" value="n2" />
	<postfield name="aPpk" value="<%=aPpk %>" />
	<postfield name="bPpk" value="<%=bPpk %>" />
	<postfield name="sc_pk" value="<%=sc_pk%>" />
	<postfield name="type" value="<%=Shortcut.FLEE%>" />
	<postfield name="pk" value="<%=pk %>" />
	<postfield name="shortType" value="<%=shortType %>" />
	</go>
	逃跑
	</anchor>
	<br/>  
	<%
	if(shortType != null && !shortType.equals("") && !shortType.equals("null")){
	%>
		<anchor>
		<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/function/function.jsp")%>"> 
		</go>
		返回
		</anchor>
	<%
	} else { 
	
	if(pk != null && !pk.equals("") && !pk.equals("null")){ %>
		<%-- 将pk.do的 n3 转换为n7，因为那样会造成二次进入pk状态   --%>
		<anchor>
		<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do")%>">
		<postfield name="cmd" value="n7" /> 
		<postfield name="aPpk" value="<%=aPpk %>" /> 
		<postfield name="bPpk" value="<%=bPpk %>" /> 
		<postfield name="shortType" value="<%=shortType %>" />
		</go>
		返回
		</anchor>
	<%
	
	
	}else{ %>
		<anchor>
		<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>">
		<postfield name="cmd" value="n4" /> 
		<postfield name="aPpk" value="<%=aPpk %>" />
		<postfield name="bPpk" value="<%=bPpk %>" />
		<postfield name="shortType" value="<%=shortType %>" />
		</go>
		返回
		</anchor>
	<%}} %>
	<br/>
	<%@ include file="/init/init_timeq.jsp"%>
</p>
</card>
</wml>
		