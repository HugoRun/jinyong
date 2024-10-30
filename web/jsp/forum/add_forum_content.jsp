<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.pm.dao.forum.*,com.ls.pub.util.*,com.pub.ben.info.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 

<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<card id='login' title="<bean:message key="gamename"/>">
  <p>
	<%
		String hint = (String)request.getAttribute("hint");
		String classId = request.getParameter("classId");
		if(classId == null || classId.equals("")) {
			classId = (String)request.getAttribute("classId");
		}
		String classPageNo = (String)request.getParameter("classPageNo");
		if(classPageNo == null || classPageNo.equals("")){
			classPageNo = (String)request.getAttribute("classPageNo");
		}
		String title = request.getParameter("title");
		if(title == null || title.trim().equals("")) {
			title = (String)request.getAttribute("title");
		}
		if(title == null || title.equals("")) {
			String hint1 = "标题不能为空!<br/>";
			request.getRequestDispatcher("/jsp/forum/add_forum_title.jsp?hint="+hint1).forward(request,response);			
		} else {
			int flag = Expression.hasPublish(title);
		 	if(flag == -1){
		 		String hint1 = "您的标题里有非法字符！请重新输入!<br/>";
				request.getRequestDispatcher("/jsp/forum/add_forum_title.jsp?hint="+hint1).forward(request,response);	
		 	}
		}
		title = Expression.encoding(title);
		ForumDAOImpl forumDAOImpl = new ForumDAOImpl();
		String className = forumDAOImpl.getForumNameById(classId);
		if(hint != null && hint.length() != 0){
	%>	
	<%=hint %><br/>
	<%} %>
	请输入帖子内容:<br/>
	 <input name="content" type="text"  maxlength="500" />500个字符<br/>
  	  <anchor>
		<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="cmd" value="n3" />
			<postfield name="title" value="<%=title %>"/>
			<postfield name="content" value="$content"/>
			<postfield name="classId" value="<%=classId %>"/>
		</go>
		传送
	</anchor>
	<br/>
		<anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="cmd" value="n2" />
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