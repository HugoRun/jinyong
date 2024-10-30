<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.model.group.GroupModel"%>
<%@page import="com.ls.model.user.BasicInfo"%>
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="ID" title="<s:message key = "gamename"/>">
<p>
可申请加入的队伍:<br/>
<%
	List<GroupModel> groups = (List<GroupModel>)request.getAttribute("groups");
	int pageall = Integer.parseInt(request.getAttribute("pageall").toString());
	int pageno = Integer.parseInt(request.getAttribute("page").toString());
	
	BasicInfo basic_info = null;
	for(GroupModel group_info:groups)
	{
	basic_info = group_info.getCaptianInfo().getBasicInfo();
%>
<anchor> 
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do")%>">
<postfield name="p_pk" value="<%=basic_info.getPPk()%>" />
<postfield name="cmd" value="n13" />
<postfield name="page" value="<%=pageno %>" />
</go>
<%=basic_info.getName()%>
</anchor>
<%="("+basic_info.getGrade()+"级 "+basic_info.getSceneInfo().getMap().getBarea().getBareaName()+")"  %><br/>
<%
	}
	if(pageall!=0){
	if(pageno !=pageall-1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do") %>"> 
	<postfield name="page" value="<%=pageno+1%>" />
	<postfield name="cmd" value="n12" />
	</go>
	下一页
	</anchor> 
	<%
	}
	if(pageno != 0){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do") %>"> 
	<postfield name="page" value="<%=pageno-1%>" />
	<postfield name="cmd" value="n12" />
	</go>
	上一页
	</anchor> 
	<%
	}
	if(pageall > 1 && pageall > pageno+1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do") %>"> 
	<postfield name="page" value="<%=pageall-1%>" />
	<postfield name="cmd" value="n12" />
	</go>
	末页
	</anchor>
	<%	
	}
	if(pageall > 1 && pageall == pageno+1){
	%>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do") %>"> 
	<postfield name="page" value="<%=0%>" />
	<postfield name="cmd" value="n12" />
	</go>
	首页
	</anchor>
	<%	
	}
	%>第<%=pageno+1 %>页/共<%=pageall %>页<%
	}
%>
<br/>
<anchor><go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do?cmd=n2")%>"></go>返回</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
