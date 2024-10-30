<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>
<%@page import="com.ls.model.user.RoleEntity"%>
<%@page import="com.ls.model.group.GroupModel"%>
<%@page import="com.ls.web.service.group.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="group" title="<s:message key = "gamename"/>">
<p>
<%
	GroupModel group_info = (GroupModel)request.getAttribute("group_info");
	
	String pageno = (String)request.getAttribute("page");
	if( group_info!=null )
	{
		int captian_id = group_info.getCaptianID();
		List<RoleEntity> role_list = group_info.getMemberList();
		for( RoleEntity role:role_list )
		{
			if(role.getBasicInfo().getPPk()==captian_id){
%><%=role.getBasicInfo().getName()+"(队长  "+role.getBasicInfo().getGrade()+"级)" %><br/><%
			}
			else{
%><%=role.getBasicInfo().getName()+"("+role.getBasicInfo().getGrade()+"级)" %><br/><%
			}
		}
%>
<anchor> 
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do")%>">
<postfield name="p_pk" value="<%=captian_id%>"/>
<postfield name="cmd" value="n14"/>
</go>
加入
</anchor>
<%
	}
	else
	{
%>该队伍已解散<%
	}
%>
<br/>
<anchor> 
<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/group.do")%>">
<postfield name="page" value="<%=pageno%>"/>
<postfield name="cmd" value="n12"/>
</go>返回
</anchor>
<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
