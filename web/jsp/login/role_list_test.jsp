<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.List,com.ls.ben.vo.info.partinfo.PartInfoVO,com.ls.pub.util.DateUtil,
com.lw.vo.systemnotify.SystemNotifyVO" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.pub.constant.Channel"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
<%
		List<PartInfoVO> role_list = (List<PartInfoVO>)request.getAttribute("role_list");
		List<SystemNotifyVO> notifylist_gengxin = (List<SystemNotifyVO>)request.getAttribute("notifylist_gengxin");
		List<SystemNotifyVO> notifylist_huodong = (List<SystemNotifyVO>)request.getAttribute("notifylist_huodong");
%> 
<% 
if(notifylist_gengxin !=null){
for(int i = 0;i<notifylist_gengxin.size();i++){
SystemNotifyVO vo = notifylist_gengxin.get(i);
%>
<anchor>
<go method="post" href="<%=response.encodeURL("sysnotify.do") %>">
<postfield name="cmd" value="n1" />
<postfield name="id" value="<%=vo.getId() %>" />
</go>
<%=vo.getNotifytitle() %>
</anchor>
<br/>
<%
} }%>
选择角色:<br/>
<%
	for (int i = 0; i < role_list.size(); i++) 
	{
		PartInfoVO vo = (PartInfoVO) role_list.get(i);
%> 
<anchor> 
<go method="post" href="<%=response.encodeURL("login.do?cmd=n4") %>">
<postfield name="pPk" value="<%=vo.getPPk() %>"/>
</go>
<%=vo.getPName()%>
</anchor>
(<%=vo.getPGrade()%>级 <%if(vo.getPSex()==1){ %>男<%}else if(vo.getPSex()==2){ %>女<%} %> )
<%
		if(vo.getDeleteFlag() == 0){ %>
	 <anchor>
	 <go method="post" href="<%=response.encodeURL("role.do?cmd=n6") %>">
	 <postfield name="uPk" value="<%=vo.getUPk() %>" />	
	 <postfield name="pPk" value="<%=vo.getPPk() %>" />
	 <postfield name="pName" value="<%=vo.getPName() %>" />  
	 <postfield name="pGrade" value="<%=vo.getPGrade() %>" />
	 </go>
	删除
	</anchor>
	<%} else { %>
	<%} %>
		<br/><%
	}
%>
<br/>
<anchor><go href="<%=response.encodeURL("role.do?cmd=n1") %>" method="get"></go>创建角色</anchor><br/>
<%
	if(GameConfig.getChannelId()==Channel.TELE)
	{
		%>
		<%@ include file="/cooperate/tele/index/mainpage.jsp"%>
		<%
	}
 %>
<% 
if(notifylist_huodong !=null){
for(int i = 0;i<notifylist_huodong.size();i++){
SystemNotifyVO vo = notifylist_huodong.get(i);
%>
<anchor>
<go method="post" href="<%=response.encodeURL("sysnotify.do") %>">
<postfield name="cmd" value="n1" />
<postfield name="id" value="<%=vo.getId() %>" />
</go>
<%=vo.getNotifytitle() %>
</anchor>
<br/>
<%
} }%>
</p>
</card>
</wml>
