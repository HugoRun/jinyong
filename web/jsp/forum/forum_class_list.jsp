<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="com.ls.pub.util.*,java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%@page import="com.pm.vo.forum.*"%> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p> 
	<%
	List class_list = (List)request.getAttribute("forumclassList");
	String uPk = (String)request.getAttribute("uPk");
	ForumClassBean forumClassBean = null;%>
	
	论坛频道:
	<br/>
	 <%
	 	if( class_list!=null && class_list.size()!= 0)
	 	{
	 		for( int i=0;i<class_list.size();i++)
	 		{
	 			forumClassBean = (ForumClassBean)class_list.get(i);
	 			
	 			%>
	 				<anchor>
	 				<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n2")%>">
					<postfield name="classId" value="<%=forumClassBean.getClassID() %>" />
					</go>
					<%= StringUtil.isoToGBK(forumClassBean.getClassName()) %>
					</anchor><br/>	 			
			<% }} 	else
	 	{
	 		out.print("无");
	 	}
	  %> 
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
