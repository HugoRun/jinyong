<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN"
      "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@ page import="com.pm.vo.forum.*,com.ls.pub.util.*,com.ls.pub.bean.*"%> 
<%@ page import="java.util.List,com.pm.service.forum.*,java.text.*" %>
<wml><%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<head><meta http-equiv='Cache-Control' content='no-cache'/>
<meta http-equiv="Content-Type" content="text/vnd.wap.wml; charset=utf-8" />
</head>
<card id='login' title="<s:message key = "gamename"/>">
<p>
<% 
		ForumBean fb = (ForumBean)request.getAttribute("forumBean");
		ForumClassBean forumClassBean = (ForumClassBean)request.getAttribute("forumClassBean");
		String pageNo = (String)request.getAttribute("pageNo");
		String classId = (String)request.getAttribute("classId");
		ForumRevertService frService = new ForumRevertService();
		QueryPage qpage = frService.getAllForumRevert(fb.getId(),1);
		
		DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	%>
	<anchor>
		<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/add_forum_title.jsp")%>">
		<postfield name="classId" value="<%=classId %>" />
		<postfield name="classPageNo" value="<%=pageNo %>" />
		</go>
		发贴
	</anchor>
	<br/>
	标题:<%=StringUtil.isoToGBK(fb.getTitle()) %>
	【<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/part_info_view.jsp")%>"> 
	<postfield name="pPks" value="<%=fb.getUserID() %>" />
	<postfield name="classPageNo" value="<%=pageNo %>" />
	<postfield name="classId" value="<%=classId %>" />
	<postfield name="className" value="<%=StringUtil.isoToGBK(fb.getClassName()) %>" />
	</go>
	<%=StringUtil.isoToGBK(fb.getUserName()) %>
	</anchor>,<%=df.format(df1.parse(fb.getRealAddTime())) %>】
	<br/>
	内容:
	<%
	if(fb.getContent() != null)
      	 {%>
			<%=StringUtil.isoToGBK(fb.getContent()) %>
	<%} %>
	<br/>
	 	  <anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/return_revert.jsp")%>">
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageId" value="<%=fb.getId() %>" />
			<postfield name="classPageNo" value="<%=pageNo %>" />
			</go>
			回帖
		 </anchor>
		 <br/>
		 <anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forumInfo.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="classPageNo" value="<%=pageNo %>" />
			<postfield name="managerType" value="1" />
			<postfield name="pagePPk" value="<%=fb.getUserID() %>" />
			</go>
			禁止发帖
		 </anchor>
		 <anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forumInfo.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="classPageNo" value="<%=pageNo %>" />
			<postfield name="managerType" value="2" />
			</go>
			删贴
		 </anchor>
		 
		 
		 <anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forumInfo.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="classPageNo" value="<%=pageNo %>" />
			<postfield name="managerType" value="3" />
			</go>
			<%if (fb.getTaxis() == 0 ) { %>
			置顶
			<% } else { %>
			取消置顶<%} %>
		 </anchor>
		 
		 <anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forumInfo.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="classPageNo" value="<%=pageNo %>" />
			<postfield name="managerType" value="4" />
			</go>
			<%if (fb.getVouch() == 0 ) { %>
			锁帖
			<% } else { %>
			取消锁帖<%} %>
		 </anchor>
		 
		 
		 
		 
	<!-- 		
       <%--  
      		 long currentPageNo = qpage.getCurrentPageNo();  
      	        		if ( qpage.getTotalCount() == 0) {
      	        			currentPageNo = 0;
      	        		}   
      	 out.println(currentPageNo+"/"+qpage.getTotalPageCount());
      	 if( qpage.hasNextPage() )
		{
	--%>
	<anchor>
	<go method="post"   href="<%//=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n5")%>">
	<postfield name="classId" value="<%//=classId %>" />
	<postfield name="pageId" value="<%//=fb.getId() %>" />
	<postfield name="pageNo" value="<%//=qpage.getCurrentPageNo()+1%>" />
	<postfield name="classPageNo" value="<%//=pageNo %>" />
	</go>
	下一页
	</anchor>
	  
	<%--
		}
		if( qpage.hasPreviousPage() )
		{
	--%> 
	<anchor>
	<go method="post"   href="<%//=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n5")%>">
	<postfield name="classId" value="<%//=classId %>" />
	<postfield name="pageId" value="<%//=fb.getId() %>" />
	<postfield name="pageNo" value="<%//=qpage.getCurrentPageNo()-1%>" />
	<postfield name="classPageNo" value="<%//=pageNo %>" />
	</go>
	上一页
	</anchor>
	<%
		//}
	 %>
	 
	 到
	 <input name="jumppage" type="text"  format="5N" size="2" maxlength="5" />
	 页
	<anchor>
	<go method="post"   href="<%//=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n5")%>">
	<postfield name="classId" value="<%//=classId %>" />
	<postfield name="pageNo" value="$jumppage" />
	<postfield name="pageId" value="<%//=fb.getId() %>" />
	<postfield name="classPageNo" value="<%//=pageNo %>" />
	</go>
	跳转
	</anchor>
<br/> -->
<br/>
     <anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="uPk" value="uPk" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageNo" value="<%=pageNo %>" />
			</go>
			返回<%=StringUtil.isoToGBK(forumClassBean.getClassName()) %>
		</anchor>
		<br/>

<br/>
 <%@ include file="/init/init_timeq.jsp"%> 
</p>
</card>
</wml>