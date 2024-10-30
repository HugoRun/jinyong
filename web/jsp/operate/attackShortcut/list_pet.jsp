<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.partinfo.*"%>
<%@page import="java.util.*"%>
<%@page import="com.ls.pub.constant.Shortcut"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p>
<%
	String aPpk = (String) request.getAttribute("aPpk");
	String bPpk = (String) request.getAttribute("bPpk");
	String sc_pk = (String)request.getAttribute("sc_pk");
	String pk = (String)request.getAttribute("pk");
	List cures = (List)request.getAttribute("cures");
	String shortType = (String) request.getAttribute("shortType");	// 用来确定究竟是普通情况设置快捷键还是战斗情况下设置快捷键
	
	PlayerPropGroupVO  cure = null;
 %>
道具列表<br/>
<%
	if( cures!=null && cures.size()!=0 )
	{
		for(int i=0;i<cures.size();i++)
		{
			cure = (PlayerPropGroupVO)cures.get(i);
			%>
			<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackshortcut.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="sc_pk" value="<%=sc_pk %>" />
			<postfield name="operate_id" value="<%=cure.getPropId() %>" />
			<postfield name="type" value="<%=Shortcut.ATTACKPROP %>" />
			<postfield name="aPpk" value="<%=aPpk %>" />
			<postfield name="bPpk" value="<%=bPpk %>" />
			<postfield name="pk" value="<%=pk %>" />
			<postfield name="shortType" value="<%=shortType %>" />
			</go>
			<%= cure.getPropName()%>
			</anchor>
			<%
			if( (i+1)!=cures.size() )
			{
				out.print("<br/>");
			}
		}
	}
	else
	{
		out.print("暂无道具");
	}
 %>
<br/>
<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/attackNPC.do")%>"> 
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
