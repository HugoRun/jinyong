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
		String pagePPk = (String)request.getAttribute("pagePPk");
		String page_id = (String)request.getAttribute("page_id");
		String classId = (String)request.getAttribute("classId");
		String classPageNo = (String)request.getAttribute("classPageNo");
		String managerType = (String)request.getAttribute("managerType");
		
		if(pagePPk == null) {
			pagePPk = (String)request.getParameter("pagePPk");
		}
		System.out.println("type="+(request.getAttribute("type") == null));
		if(hint != null && hint.length() != 0){
	%>
	<%=hint %>
	<%} %>
	 
	<br/>
	<%if (request.getAttribute("type") == null ) { %>
		<anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forumInfo.do")%>">
			<postfield name="cmd" value="n1" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="page_id" value="<%=page_id %>" />
			<postfield name="pageNo" value="<%=classPageNo %>" />
			</go>
			返回
		</anchor>
		<%} else { %>
			<anchor>
			<go method="post"   href="<%=response.encodeURL(GameConfig.getContextPath()+"/forum.do")%>">
			<postfield name="cmd" value="n2" />
			<postfield name="classId" value="<%=classId %>" />
			<postfield name="pageNo" value="<%=classPageNo %>" />
			</go>
			返回
			</anchor>
			
		<%} %>
		<br/>
	 <%@ include file="/init/init_time.jsp"%> 
  </p>
  </card>
</wml>