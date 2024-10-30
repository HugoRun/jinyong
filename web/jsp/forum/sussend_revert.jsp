<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@ page import="com.ls.pub.util.*"%>
<%@ page import="com.pm.dao.forum.*" %>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<head><meta http-equiv='Cache-Control' content='no-cache'/>
<meta http-equiv="Content-Type" content="text/vnd.wap.wml; charset=utf-8" />
</head>
<card id='login' title="<bean:message key="gamename"/>">

<p>
<% 
	String pageID = request.getParameter("pageId");
	String classId = request.getParameter("classId");
	ForumDAOImpl forumDAOImpl = new ForumDAOImpl();
	String className = forumDAOImpl.getForumNameById(classId);
	String classPageNo = (String)request.getParameter("classPageNo");
	if(classPageNo == null || classPageNo.equals("")){
		classPageNo = (String)request.getAttribute("classPageNo");
	}
	
	%>
	您已经成功回复了帖子！<br/>
      	  <anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="page_id" value="<%=pageID %>" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="cmd" value="n4" />
			<postfield name="classPageNo" value="<%=classPageNo %>" />
			</go>
			返回帖子
		 </anchor>
	<br/>	
     <anchor> 
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="uPk" value="uPk" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageNo" value="<%=classPageNo %>" />
			</go>
			返回<%=StringUtil.isoToGBK(className) %>
		</anchor>
		
<br/>
 <%@ include file="/init/init_timeq.jsp"%> 
</p>
</card>
</wml>