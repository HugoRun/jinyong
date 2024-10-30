<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="com.ls.pub.util.StringUtil,java.util.*,com.ls.ben.vo.info.attack.pk.*,com.ls.web.service.pk.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%
	String aPpk = (String) request.getAttribute("aPpk");
	String bPpk = (String) request.getAttribute("bPpk");
	String pk = (String)request.getAttribute("pk");
	String shortType = (String) request.getAttribute("shortType");	// 用来确定究竟是普通情况设置快捷键还是战斗情况下设置快捷键
	List shortcuts = (List)request.getAttribute("shortcuts");
		
	ShortcutVO shortcut = null;
	if(shortcuts==null )
	{
		out.print("参数为空");
	}
 %>
将物品或者技能设置为快捷方式后可在战斗过程中直接使用。<br/>
当前快捷栏（点击快捷栏可更改当前快捷栏设置）<br/>
<%
	for(int i=0;i<shortcuts.size();i++)
	{
		shortcut = (ShortcutVO)shortcuts.get(i);
		%>
		<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
		<postfield name="cmd" value="n3" />
		<postfield name="aPpk" value="<%=aPpk %>" />
		<postfield name="bPpk" value="<%=bPpk %>" />
		<postfield name="sc_pk" value="<%=shortcut.getScPk() %>" />
		<postfield name="pk" value="<%=pk %>" />
		<postfield name="shortType" value="<%=shortType %>" />
		</go>
		<%= StringUtil.isoToGB(shortcut.getScDisplay())%>
		</anchor><br/>
		<%
	}
 %>


<anchor>
<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
<postfield name="aPpk" value="<%=aPpk %>" />
<postfield name="bPpk" value="<%=bPpk %>" />
<postfield name="cmd" value="n4"/>
<postfield name="pk" value="<%=pk %>" />
<postfield name="shortType" value="<%=shortType %>" />
</go>
清空快捷栏
</anchor>  
<br/>

<%
	if(shortType != null && !shortType.equals("") && !shortType.equals("null")){
	%>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/syssetting.do?cmd=index")%>"></go>返回上级</anchor>
	<%
	} else { 
		if(pk != null && !pk.equals("") && !pk.equals("null")){ 
			%>
		<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/pk.do")%>">
		<postfield name="cmd" value="n7" /> 
		<postfield name="aPpk" value="<%=aPpk %>" /> 
		<postfield name="bPpk" value="<%=bPpk %>" /> 
		<postfield name="shortType" value="<%=shortType %>" />
		</go>
		返回
		</anchor> 
				
		<%} else { %>
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
