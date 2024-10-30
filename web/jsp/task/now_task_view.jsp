<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" import="java.util.*,com.ben.vo.task.*,com.ls.pub.util.*,com.pub.ben.info.*,com.ls.pub.bean.*"
	pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %> 
<%
	response.setContentType("text/vnd.wap.wml");
%>
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="login" title="<s:message key = "gamename"/>">
<p>
	【任务】
	<br/>
	<%
		//QueryPage task_list = (QueryPage)request.getAttribute("task_list");
		//List prop_list = (List)task_list.getResult();
		int page_no = (Integer)request.getAttribute("page_no");
		List<TaskVO> list = (List<TaskVO>)request.getAttribute("list");
		HttpSession session1 = request.getSession();
 	%>
 	<%if(list != null){
	
	    if(list != null && list.size() != 0) {
	    //分页显示
	    int end = list.size() > page_no*10 ? page_no*10 : list.size();
	    int start = (page_no-1)*10;
	    for(int i = start;i < end; i++){
	    TaskVO vo = (TaskVO)list.get(i);
	  %>
		
	<anchor>
	<go method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do")%>">  
	<postfield name="cmd" value="n9" />
	<postfield name="tId" value="<%=vo.getTId() %>" />
	</go>
	<%=StringUtil.isoToGBK(vo.getTName()) %>(<%=vo.getTLevelXiao() %>级)  
	</anchor>
	<br/>
	<%} } else {%>
		目前没有适合您当前等级的任务！
	<%} %>
	
	
	 <%
		if( list.size() > page_no*10 )
		{
	 %>
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do")%>">
	<postfield name="page_no" value="<%=page_no+1%>" />
	<postfield name="cmd" value="n5" />
	</go>
	下一页
	</anchor>
	  
	<%
		}
		if( page_no > 1 )
		{
	%> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do")%>">
	<postfield name="page_no" value="<%=page_no-1%>" />
	<postfield name="cmd" value="n5" />
	</go>
	上一页
	</anchor>
	<%
		}    }else {
	 %>
	 	没有可接的任务了
	 <%} %>	
	<br/> 
	<anchor>
	<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/taskinfoaction.do")%>">  
	<postfield name="cmd" value="n1" /> 
	</go> 
	返回 
	</anchor> 
	
	
	
	<%@ include file="/init/init_time.jsp"%>
</p>
</card>
</wml>
