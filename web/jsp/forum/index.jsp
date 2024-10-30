<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml"
	import="java.util.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@ page import="com.pm.vo.forum.*,com.pm.dao.forum.*"%>
<%@ page import="com.pub.*,com.ls.pub.util.*,com.ls.pub.bean.*"%>
<%@ page import="java.util.List" %>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<head><meta http-equiv='Cache-Control' content='no-cache'/>
<meta http-equiv="Content-Type" content="text/vnd.wap.wml; charset=utf-8" />
</head>
<card id='login' title="<bean:message key="gamename"/>">
<p>
<% 
	QueryPage forum_page = (QueryPage)request.getAttribute("forum_page");
	List list = (List)forum_page.getResult();
	List managerlist = (List)request.getAttribute("managerList");
	boolean isManager = (Boolean)request.getAttribute("isManager");
	ForumClassBean fcvo = (ForumClassBean)request.getAttribute("forumClassBean");
	ForumDAOImpl forumDao = new ForumDAOImpl();
	
	ForumBean fb = new ForumBean();
	out.println(StringUtil.isoToGBK(fcvo.getClassName()));
	out.println("<br/>论坛管理员:");
	for ( int i=0;i<managerlist.size();i++) {
		out.println(managerlist.get(i));
	}
	%>
	<br/>
		<anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/add_forum_title.jsp")%>">
			<postfield name="classId" value="<%=fcvo.getClassID() %>" />
			<postfield name="classPageNo" value="<%=forum_page.getCurrentPageNo() %>"/>
			</go>
			发帖
		</anchor>
	<br/>
	<%
	if(forum_page.getCurrentPageNo() == 1 || forum_page.getCurrentPageNo() == 0){
		List taxilist = forumDao.getAllTaxis(fcvo.getClassID()); 
         //显示置顶帖
         for(int i = 0; i < taxilist.size(); i++)
        {
        StringBuffer sb = new StringBuffer();
             fb = (ForumBean)taxilist.get(i);%>
          <%=(i+1) %>
         <anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n4")%>">
			<postfield name="classId" value="<%=fcvo.getClassID() %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="classPageNo" value="<%=forum_page.getCurrentPageNo() %>" />
			</go>
			<%=StringUtil.isoToGBK(StringUtil.subString(fb.getTitle(),0,20)) %>
		</anchor>		
		<% sb.append("(").append(fb.getRevertNum()).append("/").append(fb.getReadNum()).append(")");	
		sb.append("【顶】");
		if (fb.getVouch() != 0) {sb.append("【锁】"); } 
		sb.append("【").append(fb.getUserName()).append(",").append(fb.getAddTime()).append("】");
		out.println(sb.toString());		
       if(isManager) { %>
       		<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/forumInfo.do?cmd=n1")%>" >
       		<postfield name="classId" value="<%=fcvo.getClassID() %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="pageNo" value="<%=forum_page.getCurrentPageNo() %>" />
			</go>
       		管理
       		</anchor>       		
      <% }%>
		<br/>
	<%} }
         //显示普通帖
         for(int i = 0; i < list.size(); i++)
        {
          StringBuffer  sb = new StringBuffer();
          fb = (ForumBean)list.get(i);%>
          <%=i+1 %>)
         <anchor> 
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n4")%>">
			<postfield name="classId" value="<%=fcvo.getClassID() %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="classPageNo" value="<%=forum_page.getCurrentPageNo() %>" />
			</go>
			<%=StringUtil.isoToGBK(StringUtil.subString(fb.getTitle(),0,20)) %>
		</anchor>
		
		<% 
		sb.append("\n \t");
		sb.append("(").append(fb.getRevertNum()).append("/").append(fb.getReadNum()).append(")");
		if (fb.getVouch() != 0) {sb.append("【锁】"); } 
		sb.append("【").append(fb.getUserName()).append(",").append(fb.getAddTime()).append("】");
		out.println(sb.toString());		
       if(isManager) { %>
       		<anchor><go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/forumInfo.do?cmd=n1")%>" >
       		<postfield name="classId" value="<%=fcvo.getClassID() %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="classPageNo" value="<%=forum_page.getCurrentPageNo() %>" />
			</go>
       		管理
       		</anchor>       		
      <% }
       
       out.println("<br/>");
        }
        long s = 1;
        if(forum_page.getTotalPageCount() != 0){
           s = forum_page.getTotalPageCount();
        }
	 	out.println(forum_page.getCurrentPageNo()+"/"+s);
	
		if( forum_page.hasNextPage() )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n2")%>">
	<postfield name="classId" value="<%=fcvo.getClassID() %>" />
	<postfield name="pageNo" value="<%=forum_page.getCurrentPageNo()+1%>" />
	</go>
	下一页
	</anchor>
	  
	<%
		}
		if( forum_page.hasPreviousPage() )
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n2")%>">
	<postfield name="classId" value="<%=fcvo.getClassID() %>" />
	<postfield name="pageNo" value="<%=forum_page.getCurrentPageNo()-1%>" />
	</go>
	上一页
	</anchor>
	<%	}	%>
	 到
	 <input name="jumppage" type="text"  size="4" maxlength="4" />
	 页
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n2")%>">
	<postfield name="classId" value="<%=fcvo.getClassID() %>" />
	<postfield name="pageNo" value="$jumppage" />
	</go>
	跳转
	</anchor>
<br/>
 		<anchor>
		<go method="get" href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n1")%>" ></go>
		返回论坛首页
		</anchor>
 <%@ include file="/init/init_time.jsp"%> 
</p>
</card>
</wml>