<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.skill.*"%> 
<%@page import="com.ls.pub.constant.Shortcut"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>  
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id="map" title="<bean:message key="gamename"/>">
<p>
<%
    String sc_pk = (String)request.getAttribute("sc_pk");
	String aPpk = (String) request.getAttribute("aPpk");
	String bPpk = (String) request.getAttribute("bPpk");
	List skills = (List)request.getAttribute("skills");
	String isCatchPet = (String)request.getAttribute("isCatchPet");
	String pk = (String)request.getAttribute("pk");
	
	String shortType = (String) request.getAttribute("shortType");	// 用来确定究竟是普通情况设置快捷键还是战斗情况下设置快捷键
	PlayerSkillVO  skill = null;
 %>
技能列表<br/>
<%
	if( skills!=null && skills.size()!=0 )
	{
		for(int i=0;i<skills.size();i++)
		{
			skill = (PlayerSkillVO)skills.get(i);
			%>
			<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="sc_pk" value="<%=sc_pk %>" />
			<postfield name="type" value="<%=Shortcut.SKILL %>" />
			<postfield name="operate_id" value="<%=skill.getSPk() %>" />
			<postfield name="aPpk" value="<%=aPpk %>" />
			<postfield name="bPpk" value="<%=bPpk %>" />
			<postfield name="pk" value="<%=pk %>" />
			<postfield name="shortType" value="<%=shortType %>" />
			</go>
			<%= skill.getSkName()%><br/>
			</anchor>
			<%
		}
	} 
 %>
	<br/> 
		<anchor>
		<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>"> 
		<postfield name="cmd" value="n6" />
		<postfield name="sc_pk" value="<%=sc_pk%>" /> 
		<postfield name="aPpk" value="<%=aPpk %>" />
		<postfield name="bPpk" value="<%=bPpk %>" />
		<postfield name="pk" value="<%=pk %>" />
		<postfield name="shortType" value="<%=shortType %>" />
		</go>
		返回
		</anchor>
 <br/>
<%@ include file="/init/init_timeq.jsp"%> 
</p>
</card>
</wml>
