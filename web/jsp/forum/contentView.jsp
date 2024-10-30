<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%@ page import="com.pm.vo.forum.*,com.ls.pub.util.*,com.ls.pub.bean.*"%> 
<%@ page import="java.util.List,com.pm.service.forum.*,java.text.*" %>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<head><meta http-equiv='Cache-Control' content='no-cache'/>
<meta http-equiv="Content-Type" content="text/vnd.wap.wml; charset=utf-8" />
</head>
<card id='login' title="<bean:message key="gamename"/>">
<p>
<% 
		ForumBean fb = (ForumBean)request.getAttribute("forumBean");
		String pageNo = (String)request.getAttribute("pageNo");
		String classId = (String)request.getAttribute("classId");
		String classPageNo = (String)request.getAttribute("classPageNo");
		String className = (String)request.getAttribute("className");
		ForumRevertService frService = new ForumRevertService();
		//QueryPage qpage = frService.getAllForumRevert(fb.getId(),1);
		QueryPage qpage = (QueryPage)request.getAttribute("revert_page");
		DateFormat df = new SimpleDateFormat("MM-dd HH:mm");
		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	%>		
		<anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/add_forum_title.jsp")%>">
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="classPageNo" value="<%=classPageNo %>" />
			</go>
			发贴
		</anchor>
	<br/>
	标题:<%=StringUtil.isoToGBK(fb.getTitle()) %>【<anchor> 
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/part_info_view.jsp")%>"> 
	<postfield name="pPks" value="<%=fb.getUserID() %>" />
	<postfield name="classPageNo" value="<%=classPageNo %>" />
	<postfield name="classId" value="<%=classId %>" />
	<postfield name="className" value="<%=StringUtil.isoToGBK(fb.getClassName()) %>" />
	</go>
	<%=StringUtil.isoToGBK(fb.getUserName()) %>
	</anchor>,<%=df.format(df1.parse(fb.getRealAddTime())) %>】
	<br/>
	内容:
	<%
	if(fb.getContent() != null)
      	 {
      	 // 如果erpage不为空,且为2,就显示后250字
      	 String erpage = (String)request.getAttribute("erpage");
      	 if ( fb.getContent().length() <= 250 ) {
      	 	out.println(fb.getContent());
      	 } else {
      	 	if ( erpage == null  ) {
      	 		
      	 		out.println(fb.getContent().substring(1,250)); %>
      	 	<br/>
      	 	<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n4")%>">
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="pageNo" value="<%=qpage.getCurrentPageNo() %>" />
			<postfield name="erpage" value="2" />
			</go> 
			下页
			</anchor>		
      	 		
      	<% 	} else {
      		out.println(fb.getContent().substring(250));  %>
      	 	<br/>
      	 	<anchor>
			<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n4")%>">
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="page_id" value="<%=fb.getId() %>" />
			<postfield name="pageNo" value="<%=qpage.getCurrentPageNo() %>" />
			</go> 
			上页
			</anchor>		
      	
      <%	}
      	 }      	 
      	} %>
	<br/>
	 	<anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/return_revert.jsp")%>">
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageId" value="<%=fb.getId() %>" />
			<postfield name="classPageNo" value="<%=classPageNo %>" />
			</go>
			回帖
		 </anchor>
		 <br/>
			
       <%  ForumRevertService frs=new ForumRevertService();
      	      List list=(List)qpage.getResult();
      	     
      	      int count=frs.getNum(fb.getId());
      	 		 if(list!=null)
      	        		{
      	        			int m=count;
      	        			ForumRevertBean frb=null;
      	        			for(int i=0;i<list.size();i++)
      	        			{
      	        				frb=(ForumRevertBean)list.get(i);
      	        				if(frb!=null)
      	        				{%>
      	        				<%=(qpage.getTotalCount() - ((qpage.getCurrentPageNo()-1)*5+(i)))+")" %>
      	        				<%=StringUtil.isoToGBK(StringUtil.subString(frb.getContent(),0,60)) %>
      	        				<% if ( frb.getContent().length() > 60) { %>
      	        				<anchor>
								<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/revert_content_view.jsp")%>">
									<postfield name="classId" value="<%=classId %>" />
									<postfield name="pageId" value="<%=fb.getId() %>" />
									<postfield name="revertId" value="<%=frb.getId() %>" />
									<postfield name="className" value="<%=StringUtil.isoToGBK(fb.getClassName()) %>" />
									<postfield name="classPageNo" value="<%=classPageNo %>" />
									<postfield name="pageNo" value="<%=pageNo %>" />
									</go>
      	        					->
      	        				 </anchor>
      	        				 <%} %>
      	        				  
      	        				  <anchor>
      	        				  <go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/jsp/forum/part_info_view.jsp")%>"> 
									<postfield name="pPks" value="<%=frb.getUserID() %>" />
									<postfield name="classPageNo" value="<%=classPageNo %>" />
									<postfield name="classId" value="<%=classId %>" />
									<postfield name="className" value="<%=StringUtil.isoToGBK(fb.getClassName()) %>" />
									<postfield name="type" value="2" />
									<postfield name="pageId" value="<%=fb.getId() %>" />
									<postfield name="pageNo" value="<%=pageNo %>" />
									</go>
      	        				 【<%=StringUtil.isoToGBK(frb.getUserName())%>
      	        				  </anchor>,<%=df.format(df1.parse(frb.getAddTime())) %>】
      	        				<br/>
      	    	        		<%}
      	        				m--;
      	        			}
      	        		}
      	        		long currentPageNo = qpage.getCurrentPageNo();  
      	        		if ( qpage.getTotalCount() == 0) {
      	        			currentPageNo = 0;
      	        		}      	        	
      	 out.println(currentPageNo+"/"+qpage.getTotalPageCount());
      	 if( qpage.hasNextPage() )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n4")%>">
	<postfield name="classId" value="<%=classId %>" />
	<postfield name="page_id" value="<%=fb.getId() %>" />
	<postfield name="pageNo" value="<%=qpage.getCurrentPageNo()+1%>" />
	<postfield name="classPageNo" value="<%=classPageNo %>" />
	</go>
	下一页
	</anchor>
	  
	<%
		}
		if( qpage.hasPreviousPage() )
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n4")%>">
	<postfield name="classId" value="<%=classId %>" />
	<postfield name="page_id" value="<%=fb.getId() %>" />
	<postfield name="pageNo" value="<%=qpage.getCurrentPageNo()-1%>" />
	<postfield name="classPageNo" value="<%=classPageNo %>" />
	</go>
	上一页
	</anchor>
	<%
		}
	 %>
	 
	 到
	 <input name="jumppage" type="text"  format="5N" size="3" maxlength="4" />
	 页
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do?cmd=n4")%>">
	<postfield name="classId" value="<%=classId %>" />
	<postfield name="pageNo" value="$jumppage" />
	<postfield name="page_id" value="<%=fb.getId() %>" />
	<postfield name="classPageNo" value="<%=classPageNo %>" />
	</go>
	跳转
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