<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@ page import="com.pm.vo.forum.*,com.pm.dao.forum.*"%>
<%@ page import="com.pub.*,com.ls.pub.util.*,com.ls.pub.bean.*"%>
<%@ page import="java.util.List,com.pm.service.forum.*" %>
<%@ page import="java.util.List,com.pm.service.forum.*,java.text.*" %>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<head><meta http-equiv='Cache-Control' content='no-cache'/>
<meta http-equiv="Content-Type" content="text/vnd.wap.wml; charset=utf-8" />
</head>
<card id='login' title="<s:message key = "gamename"/>">
<p>
<% 
	QueryPage qpage = (QueryPage)request.getAttribute("revert_page");
	String classPageNo = (String)request.getParameter("classPageNo");
	if(classPageNo == null || classPageNo.trim().equals("")){
		classPageNo = (String)request.getAttribute("classPageNo");
	}
	String pageId = (String)request.getParameter("pageId");
	int pageSize = qpage.getPageSize();
	List list = (List)qpage.getResult();
	String classId = (String)request.getAttribute("classId");
	ForumDAOImpl forumDAOImpl = new ForumDAOImpl();
	String className = forumDAOImpl.getForumNameById(classId);
	DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	%>
	
	=回复列表=<br/>		
			
       <%
       if(list!=null) {
        for(int i = 0;i<list.size();i++) {
      	        			ForumRevertBean frb=null;
      	        				frb=(ForumRevertBean)list.get(i);
      	        				
      	        				if(frb!=null)
      	        				{%>
      	        				<%=pageSize*(qpage.getCurrentPageNo()-1)+(i+1)  %>)
      	        				<%=StringUtil.subString(StringUtil.isoToGBK(frb.getContent()),0,60) %>
      	        				<% if ( frb.getContent().length() > 60) { %>
      	        				<anchor>
								<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/revert_content_view.jsp")%>">
									<postfield name="classId" value="<%=classId %>" />
									<postfield name="pageId" value="<%=pageId %>" />
									<postfield name="revertId" value="<%=frb.getId() %>" />
									<postfield name="className" value="<%=StringUtil.isoToGBK(className) %>" />
									<postfield name="classPageNo" value="<%=classPageNo %>" />
									</go>
      	        					->
      	        					</anchor>
      	        					<%} %>
      	        					<anchor>
      	        				  <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/part_info_view.jsp")%>"> 
									<postfield name="pPks" value="<%=frb.getUserID() %>" />
									<postfield name="classPageNo" value="<%=classPageNo %>" />
									<postfield name="classId" value="<%=classId %>" />
									<postfield name="className" value="<%=StringUtil.isoToGBK(className) %>" />
									<postfield name="type" value="3" />
									<postfield name="pageId" value="<%=pageId %>" />
									<postfield name="pageNo" value="<%=qpage.getCurrentPageNo() %>" />
									</go>
      	        				 	【<%=StringUtil.subString(StringUtil.isoToGBK(frb.getUserName()),0,60) %>
      	        				 	</anchor>,<%=df.format(df1.parse(frb.getAddTime())) %>】
      	        					<%
      	        					if (i != (list.size() -1))
      	        						out.print("<br/>");
      	    	        		}	}
      	       } %>	
      	        		
  
	<br/>	
  <% out.println(qpage.getCurrentPageNo()+"/"+qpage.getTotalPageCount());   	 
   if( qpage.hasNextPage() )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n5")%>"> 
	<postfield name="pageId" value="<%=pageId  %>" />
	<postfield name="pageNo" value="<%=qpage.getCurrentPageNo()+1%>" />
	<postfield name="classId" value="<%=classId %>" />
	<postfield name="classPageNo" value="<%=classPageNo %>" />
	</go>
	下一页
	</anchor>
	  
	<%
		} if( qpage.hasPreviousPage() )	{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n5")%>"> 
	<postfield name="pageId" value="<%=pageId %>" />
	<postfield name="pageNo" value="<%=qpage.getCurrentPageNo()-1%>" />
	<postfield name="classId" value="<%=classId %>" />
	<postfield name="classPageNo" value="<%=classPageNo %>" />
	</go>
	上一页
	</anchor>
	<% } %>
	 到
	 <input name="jumppage" type="text"  format="5N" size="3" maxlength="4" />
	 页
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n5")%>">
	<postfield name="classId" value="<%=classId %>" />
	<postfield name="pageNo" value="$jumppage" />
	<postfield name="pageId" value="<%=pageId %>" />
	<postfield name="classPageNo" value="<%=classPageNo %>" />
	</go>
	跳转
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
			<postfield name="pageNo" value="<%=classPageNo %>" />
			</go>
			返回<%=StringUtil.isoToGBK(className) %>
		</anchor>
		<br/> 	  
	
 <%@ include file="/init/init_timeq.jsp"%> 
</p>
</card>
</wml>