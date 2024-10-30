<?xml version="1.0" ?>
<!DOCTYPE wml PUBLIC "-//WAPFORUM//DTD WML 1.1//EN" "http://www.wapforum.org/DTD/wml_1.1.xml">
<%@page contentType="text/vnd.wap.wml" pageEncoding="UTF-8"%><%@page import="com.ls.pub.config.GameConfig" %>  
<%
	response.setContentType("text/vnd.wap.wml");
%> 
<wml>
<%@taglib uri="/WEB-INF/tld/struts-bean.tld"  prefix="s" %>
<card id="map" title="<s:message key = "gamename"/>">
<p> 
<%
	String resultWml = (String)request.getAttribute("resultWml");
	String menu_id = (String)request.getAttribute("menu_id");
	
	int alreadyAnswerNum = (Integer)request.getAttribute("alreadyAnswerNum");
	if(menu_id == null || menu_id.equals("")){
		menu_id = request.getParameter("menu_id");
	}
	if( resultWml!=null )
	{
 		%> 
		<%=resultWml %>
		<%
	}
	else
	{
		out.print("空指针");
	}
 %> 
<br/>
<%if(alreadyAnswerNum != 10){ %>
 <anchor>
 <go  method="post" href="<%=response.encodeURL(GameConfig.getContextPath()+"/question.do?cmd=n2") %>">
 <postfield name="menu_id" value="<%=menu_id %>" />
 </go>
 继续答题
 </anchor>
<%} %>
<%@ include file="/init/init_time.jsp"%>  
</p>
</card>
</wml>
