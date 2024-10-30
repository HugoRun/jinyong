<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.ben.vo.info.skill.*"%>
<%@page import="com.ls.pub.util.StringUtil,java.util.*"%>
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
    String s_pk = (String)request.getAttribute("s_pk");
	String pk = (String)request.getAttribute("pk");
	List skills = (List)request.getAttribute("skills");
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
			<postfield name="s_pk" value="<%=s_pk %>" />
			<postfield name="type" value="<%=Shortcut.SKILL %>" />
			<postfield name="operate_id" value="<%=skill.getSPk() %>" />
			<postfield name="aPpk" value="<%=aPpk %>" />
			<postfield name="bPpk" value="<%=bPpk %>" />
			<postfield name="pk" value="<%=pk %>" />
			</go>
			<%= StringUtil.isoToGB(skill.getSkName())%><br/>
			</anchor>
			<%
		}
	}
	else
	{
		out.print("暂无技能<br/>");
	}
 %>
 返回  <br/>
----------------------<br/>
<%@ include file="/init/init_timeq.jsp"%> 
	
	
</p>
</card>
</wml>
