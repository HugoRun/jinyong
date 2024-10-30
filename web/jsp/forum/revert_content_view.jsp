<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@ page import="com.pm.vo.forum.*"%>
<%@ page import="com.ls.pub.util.*"%>
<%@ page import="com.pm.service.forum.ForumRevertService" %>
<%@ page import="java.util.List,com.pm.service.forum.*,java.text.*" %>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<head><meta http-equiv='Cache-Control' content='no-cache'/>
<meta http-equiv="Content-Type" content="text/vnd.wap.wml; charset=utf-8" />
</head>
<card id='login' title="<s:message key = "gamename"/>"> 
<p>
<% 
	DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
	DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	String classId = (String) request.getParameter("classId");
	String pageId = (String) request.getParameter("pageId");
	String pageNo = (String) request.getParameter("pageNo");
	String revertId = (String) request.getParameter("revertId");
	String className = (String) request.getParameter("className");
	ForumRevertService revertService = new ForumRevertService(); 
	ForumRevertBean frbean = revertService.getForumRevertById(revertId);
	String classPageNo = (String)request.getParameter("classPageNo");
		if(classPageNo == null || classPageNo.equals("")){
			classPageNo = (String)request.getAttribute("classPageNo");
		}
		
	//ForumClassDAOImpl forumClassDAOImpl = new ForumClassDAOImpl();
	//ForumClassBean forumclass = forumClassDAOImpl.getByID(Integer.valueOf(classId));
	if(frbean != null) {
	%>
		<%=StringUtil.isoToGBK(frbean.getContent()) %>. 回帖人:
								【<anchor>
      	        				  <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/part_info_view.jsp")%>"> 
									<postfield name="pPks" value="<%=frbean.getUserID() %>" />
									<postfield name="classPageNo" value="<%=classPageNo %>" />
									<postfield name="classId" value="<%=classId %>" />
									<postfield name="className" value="<%=StringUtil.isoToGBK(className) %>" />
									<postfield name="type" value="2" />
									<postfield name="pageId" value="<%=pageId %>" />
									<postfield name="pageNo" value="<%=pageNo %>" />
									</go>		
								<%=StringUtil.isoToGBK(frbean.getUserName()) %>
								</anchor>,<%=df.format(df1.parse(frbean.getAddTime())) %>】
		
	<%} %>
		<br/>		
      	  <anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageId" value="<%=pageId %>" />
			<postfield name="cmd" value="n5" />
			<postfield name="classPageNo" value="<%=classPageNo %>"/>
			</go>
			所有回复
		 </anchor>
		 <br/>
		  <anchor> 
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/return_revert.jsp")%>">
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageId" value="<%=pageId %>" />
			<postfield name="classPageNo" value="<%=classPageNo %>"/>
			</go>
			我要回帖
		 </anchor>
		 <br/>
		 <anchor> 
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="page_id" value="<%=pageId %>" />
			<postfield name="cmd" value="n4" />
			<postfield name="pageNo" value="<%=classPageNo %>"/>
			</go>
			返回文章
		</anchor>
		<br/>
		<anchor> 
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="uPk" value="uPk" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageNo" value="<%=classPageNo %>"/>
			</go>
			返回<%=className %>
		</anchor>
	<br/>	
 <%@ include file="/init/init_timeq.jsp"%> 
</p>
</card>
</wml>