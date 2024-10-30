<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.pm.dao.forum.*,com.ls.pub.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
 <head>
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Content-Type" content="text/vnd.wap.wml; charset=utf-8" />
</head>
<card id='login' title="<bean:message key="gamename"/>">
  <p>
	<%
		String hint = (String)request.getAttribute("hint");
		if(hint == null) {
			hint = (String)request.getParameter("hint");
		}
		String classPageNo = (String)request.getParameter("classPageNo");
		if(classPageNo == null || classPageNo.equals("")){
			classPageNo = (String)request.getAttribute("classPageNo");
		}
		String classId = request.getParameter("classId");
		if(classId == null) {
			classId = (String)request.getAttribute("classId");
		}
		ForumDAOImpl forumDAOImpl = new ForumDAOImpl();
		String className = forumDAOImpl.getForumNameById(classId);
		if(hint != null && hint.length() != 0){
	%>	
	<%=hint %>
	<%} %>
	请输入您的发贴题目:<br/>
	 <input name="title" type="text"  maxlength="15" />15个字符<br/>
  	  <anchor>
		<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/add_forum_content.jsp")%>">
			<postfield name="title" value="$title"/>
			<postfield name="classId" value="<%=classId %>"/>
			<postfield name="classPageNo" value="<%=classPageNo %>"/>
		</go>
		传送
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
	 <%@ include file="/init/init_time.jsp"%> 
  </p>
  </card>
</wml>